import {Component, Input, OnInit, Output, EventEmitter} from '@angular/core';

import {PageModel} from "./PageModel";
import { PaginationService } from "./pagination.service.component";
import * as _ from 'underscore';
import {Router} from "@angular/router";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
})
export class PaginationComponent implements OnInit{
  @Input() pageOptions;
  @Output() onSearchEvent: EventEmitter<any> = new EventEmitter();

  pageModel :PageModel = new PageModel();
  pages : number[] = [];
  page:number = 0;
  offset: number = 10;
  hasContent = false;
  direction: string = 'desc';
  property: string = "";

  constructor(private router:Router,
              private paginationService: PaginationService,
              private sanitizer: DomSanitizer) {}

  ngOnInit(): void {
    this.page = this.pageOptions.params['page'] ? this.pageOptions.params['page'] : 0;
    this.offset = this.pageOptions.params['offset'] ? this.pageOptions.params['offset'] : 10;
    this.getPageList();
  }

  initializePageInfo(): void {
    this.page = 0;
    this.offset = 10;
  }

  getPageList(): void {
    let url: string = this.pageOptions['url'];

    this.pageOptions.params['page'] = this.page;
    this.pageOptions.params['offset'] = this.offset;
    this.pageOptions.params['direction'] = this.direction;
    this.pageOptions.params['property'] = this.property;

    let urlParameters = Object.keys(this.pageOptions.params).map(key => `${key}=${encodeURIComponent(this.pageOptions.params[key])}`).join('&');

    this.paginationService.getPageList(url+'?'+urlParameters).subscribe(res => {
      this.pageModel = res as PageModel;
      this.getPages();
      this.hasContent = this.pageModel.pageInfo.total > 0;
      this.onSearchEvent.emit(this.pageOptions.params);
    });

  }

  getColumnData(data:any , column:any): any{
    let columnData = "";
    if(column.convertData != "" && column.convertData != undefined){
      columnData =  column.convertData(data);
    }else{
      if(column['dataCode'] == "" || column['dataCode'] == undefined){
        columnData =  "";
      }else{
        columnData =  data[column['dataCode']];
      }
    }
    return columnData;
  }

  clickEventCallback(data:any , column:any): void{
    if(column.clickCallBack != "" && column.clickCallBack != undefined){
      return column.clickCallBack(this, data);
    }
  }

  onPrev(): void {
    if(this.page ==  0){
      return;
    }
    this.page = this.page - 1;
    this.getPageList();
  }

  onNext(): void{
    if(this.pageModel.pageInfo.lastPage){
      return;
    }
    this.page = this.page + 1;
    this.getPageList();
  }

  onFirst(): void {
    this.page = 0;
    this.getPageList();
  }

  onLast(): void {
    this.page = Math.ceil(this.pageModel.pageInfo.total / this.pageModel.pageInfo.offset) - 1;
    this.getPageList();
  }

  getPages(): number[]{
    let pageSize = this.pageModel.pageInfo.offset;
    let totalRows = this.pageModel.pageInfo.total;
    let totalPages = Math.ceil(totalRows / pageSize);
    let currentPage = this.page + 1;

    let startPage: number, endPage: number;
    if (totalPages <= 10) {
      startPage = 1;
      endPage = totalPages;
    } else {
      if (currentPage <= 6) {
        startPage = 1;
        endPage = 10;
      } else if (currentPage + 4 >= totalPages) {
        startPage = totalPages - 9;
        endPage = totalPages;
      } else {
        startPage = currentPage - 5;
        endPage = currentPage + 4;
      }
    }

    let startIndex = (currentPage - 1) * pageSize;
    let endIndex = Math.min(startIndex + pageSize - 1, totalRows - 1);

    let pages = _.range(startPage, endPage + 1);
    this.pages = pages;
    return pages;
  }

  changeOffset(offset:number): void{
    this.offset = offset;
    this.getPageList();
  }

  sortProperty(event:any, column:any): void{
    let classList = event.currentTarget.lastElementChild.classList;
    if(!classList.contains("arrow")){
      return;
    }
    this.property = column.dataCode;
    for(let className of classList){
      if("up" == className){
        classList.remove('up');
        classList.add('down');
        // classList.replace("up","down");
        this.direction = "desc";
      }else if("down" == className){
        classList.remove('down');
        classList.add('up');
        // classList.replace("down","up");
        this.direction = "asc";
      }
    }
    this.getPageList();
  }

  changePage(pageNum:number): void{
    this.page = pageNum - 1;
    this.getPageList();
  }

  search(): void {
    console.log("search");
    this.getPageList();
  }

}
