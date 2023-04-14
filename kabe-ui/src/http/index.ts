import axios from "axios";
import type {
  AxiosInstance,
  InternalAxiosRequestConfig,
  AxiosResponse,
  AxiosError
} from "axios";

import router from "@/router";

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
  
    const { code, msg, data } = response.data;

    if (code == 200) {
      return data;
    }

    console.error(msg);

    if (code == 555) {
      router.push("/sign-in").catch((err) => err);
      console.error(msg);
    }

    return data;
  },
  (error: AxiosError) => {
    let message = "";
    const status = error.response?.status;
    switch (status) {
      case 401:
        message = 'token 失效，请重新登录'
        router.push("/sign-in").catch((err) => err);
        break;
      case 403:
        message = '拒绝访问'
        break;
      case 404:
        message = '请求地址错误'
        break;
      case 500:
        message = '服务器故障'
        break;
      default:
        message = '网络连接故障'
    }
    console.error(message);
    return Promise.reject(error);
  }
);

export default instance;
