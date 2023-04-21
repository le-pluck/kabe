import axios, { AxiosRequestConfig } from "axios";
import type {
  AxiosInstance,
  InternalAxiosRequestConfig,
  AxiosResponse,
  AxiosError,
} from "axios";

import router from "@/router";

interface Result<T = any> {
  code: number;
  message: String;
  data: T;
}

const instance: AxiosInstance = axios.create({
  baseURL: "/api",
  timeout: 30000,
});

console.log(":AxiosInstance = axios.create"); // TODO: DELETE THIS LINE

instance.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error: AxiosError) => {
    return Promise.reject(error);
  }
);

instance.interceptors.response.use(
  (response: AxiosResponse) => {
    // HOLD: 暂时不处理该情况: response.config.responseType == "blob"

    const { code, message, data }: Result = response.data;

    const requestSuccessRegex: RegExp = /^2\d{2}$/;

    if (requestSuccessRegex.test(code.toString())) {
      return data;
    } else {
      if (code == 401) {
      }
      switch (code) {
        case 401:
          router.push("/sign-in");
          console.error("需要身份验证 | ", message);
          break;
        case 461:
        case 462:
          console.error("账号密码不对 | ", message);
          break;
        default:
          console.error("switch default, 未处理的错误 | message =", message);
          break;
      }
      return Promise.reject({ message, data });
    }
  },
  (error: AxiosError) => {
    let message = "";
    const status = error.response?.status;
    switch (status) {
      case 401:
        message = "token 失效，请重新登录";
        router.push("/sign-in").catch((err) => err);
        break;
      case 403:
        message = "拒绝访问";
        break;
      case 404:
        message = "请求地址错误";
        break;
      case 500:
        message = "服务器故障";
        break;
      default:
        message = "网络连接故障";
    }
    console.error(message);
    return Promise.reject(error);
  }
);

export default {
  instance,
  get<T = any>(url: string, config?: AxiosRequestConfig): Promise<T> {
    return instance.get(url, config);
  },
  post<T = any>(
    url: string,
    data?: any,
    config?: AxiosRequestConfig
  ): Promise<T> {
    return instance.post(url, data, config);
  },
  put<T = any>(
    url: string,
    data?: any,
    config?: AxiosRequestConfig
  ): Promise<T> {
    return instance.put(url, data, config);
  },
  delete<T = any>(url: string, config?: AxiosRequestConfig): Promise<T> {
    return instance.delete(url, config);
  },
} as const;
