// 已重构： 将定义在此处的 UserAccount 等转移到 "src/apis/**/types.ts" 了

// 分页相关

type SortingCriteria = "latest" | "hottest";

interface PaginationManipulator {
  pageIndex: number;
  pages: number;
  pageSize: number;
  maxVisible: number;
  sortingCriteria: SortingCriteria;
}

interface PaginationRequester {
  pageIndex: number;
  pageSize: number;
  sortingCriteria: SortingCriteria;
}
