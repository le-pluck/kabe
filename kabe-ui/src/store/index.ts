import { reactive, readonly } from "vue";

// 非常好用！

class State<T> {
  private value: T;

  constructor(value: T) {
    this.value = value;
  }

  set(value: T) {
    this.value = value;
  }

  get() {
    return this.value;
  }

  setProperty<K extends keyof T>(key: K, value: T[K]) {
    this.value[key] = value;
  }

  getProperty<K extends keyof T>(key: K) {
    return this.value[key];
  }
}

export const store = reactive({
  permission: new State<Permission>({
    userId: -1,
    isUploader: false,
    isAdmin: false,
  }),
  theme: new State<Theme>("light"),
});
