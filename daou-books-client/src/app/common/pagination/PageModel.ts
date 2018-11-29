export class PageModel {
  pageInfo : PageInfo;
  content: any[];
}

export class PageInfo {
  page: number;
  offset: number;
  total: number;
  sort: Sort[];
  lastPage: boolean;
}

export class Sort {
  direction: string;
  property: string;
  ascending: boolean;
  descending: boolean;
}

