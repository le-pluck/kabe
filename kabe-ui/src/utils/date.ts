//
// 例子：
// (new Date()).format() ==> 2006-07-02
// (new Date()).format("yyyy-MM-dd HH:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).format("yyyy-M-d H:m:s.S")      ==> 2006-7-2 8:9:4.18
//

interface Date {
  format(template?: string): string;
}

Date.prototype.format = function (template?: string) {
  if (!template) {
    template = "yyyy-MM-dd";
  }
  const dict: any = {
    yyyy: this.getFullYear(),
    M: this.getMonth() + 1,
    d: this.getDate(),
    H: this.getHours(),
    m: this.getMinutes(),
    s: this.getSeconds(),
    MM: ("" + (this.getMonth() + 101)).substring(1),
    dd: ("" + (this.getDate() + 100)).substring(1),
    HH: ("" + (this.getHours() + 100)).substring(1),
    mm: ("" + (this.getMinutes() + 100)).substring(1),
    ss: ("" + (this.getSeconds() + 100)).substring(1),
  };
  return template.replace(/(yyyy|MM?|dd?|HH?|mm?|ss?)/g, function () {
    return dict[arguments[0]];
  });
};
