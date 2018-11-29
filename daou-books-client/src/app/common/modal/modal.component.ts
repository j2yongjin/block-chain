import {
  Input,
  Output,
  OnInit,
  OnDestroy,
  Renderer2,
  Component,
  EventEmitter,
  HostListener,
  ChangeDetectorRef, ViewChild, ElementRef,
} from '@angular/core';
import {ModalService} from './modal.service';
import {$} from "protractor";
import {Observable} from "rxjs/Observable";

@Component({
  selector: 'modal',
  templateUrl: './modal.component.html',
})
export class ModalComponent implements OnInit, OnDestroy {
  @Input() public title: string = "";
  @Input() public content: string = "";
  @Input() public buttons = [];
  @Input() public closable: boolean = true;
  @Input() public escapable: boolean = false;
  @Input() public dismissable: boolean = false;
  @Input() public identifier: string = '';
  @Input() public customClass: string = '';
  @Input() public visible: boolean = false;
  @Input() public backdrop: boolean = true;
  @Input() public force: boolean = true;
  @Input() public hideDelay: number = 500;
  @Input() public autostart: boolean = false;

  @Output() public visibleChange: EventEmitter<boolean> = new EventEmitter<boolean>();
  @Output() public onClose: EventEmitter<any> = new EventEmitter();
  @Output() public onCloseFinished: EventEmitter<any> = new EventEmitter();
  @Output() public onDismiss: EventEmitter<any> = new EventEmitter();
  @Output() public onDismissFinished: EventEmitter<any> = new EventEmitter();
  @Output() public onAnyCloseEvent: EventEmitter<any> = new EventEmitter();
  @Output() public onAnyCloseEventFinished: EventEmitter<any> = new EventEmitter();
  @Output() public onOpen: EventEmitter<any> = new EventEmitter();
  @Output() public onEscape: EventEmitter<any> = new EventEmitter();
  @Output() public onButtonClicked: EventEmitter<any> = new EventEmitter();

  public layerPosition: number = 1041;
  public overlayVisible: boolean = false;
  public modalConfirmVisible: boolean = false;
  public modalVisible: boolean = false;
  public notiVisible: boolean = false;
  public openedClass: boolean = false;
  public popupVisible: boolean = false;

  private _data: any = null;

  @ViewChild('modalContent') private modalContent: ElementRef | undefined;

  constructor(private _renderer: Renderer2,
              private _changeDetectorRef: ChangeDetectorRef,
              private _modalService: ModalService) {
  }

  public ngOnInit() {
    if (!!this.identifier && this.identifier.length) {
      this.layerPosition += this._modalService.getModalStackCount();
      this._modalService.addModal({ id: this.identifier, modal: this }, this.force);

      if (this.autostart) {
        this._modalService.open(this.identifier);
      }
    } else {
      throw new Error('identifier field isn’t set. Please set one before calling <modal> in a template.');
    }
  }

  public ngOnDestroy() {
    this._modalService.removeModal(this.identifier);
  }

  public openConfirm(top?: boolean): void {
    this.modalConfirmVisible = true;
    this.overlayVisible = true;
    this.open(top);
  }

  public openLayer(top?: boolean): void {
    this.modalVisible = true;
    this.overlayVisible = true;
    this.open(top);
  }

  public openNoti(top?: boolean): void {
    this.notiVisible = true;
    this.open(top);
    window.scroll(0, 0);
    setInterval(a => {this.close()}, 3000,[]);
  }

  public openPopup(top?: boolean): void {
    this.popupVisible = true;
    this.overlayVisible = true;
    this.open(top);
  }

  public open(top?: boolean): void {
    if (top) {
      this.layerPosition = this._modalService.getHigherIndex();
    }

    this._renderer.addClass(document.body, 'dialog-open');
    /*this.overlayVisible = true;*/
    this.visible = true;
    this.openedClass = true;
    this.onOpen.emit(this);
  }

  public close(): void {
    const me = this;

    this.openedClass = false;
    this.onClose.emit(this);
    this.onAnyCloseEvent.emit(this);

    if (this._modalService.getOpenedModals().length < 2) {
      this._renderer.removeClass(document.body, 'dialog-open');
    }
    this.modalVisible = false;
    this.modalConfirmVisible = false;
    this.notiVisible = false;
    this.popupVisible = false;
      setTimeout(() => {
      me.visibleChange.emit(me.visible);
      me.visible = false;
      me.overlayVisible = false;
      me._changeDetectorRef.markForCheck();
      me.onCloseFinished.emit(me);
      me.onAnyCloseEventFinished.emit(me);
    }, this.hideDelay);
  }

  public autoClose(button:any):void{
    if(button.autoclose){
      this.close();
    }
    this.onButtonClicked.emit(button);
  }

  public dismiss(e: any): void {
    const me = this;

    if (!this.dismissable) {
      return;
    }

    if (e.target.classList.contains('overlay')) {
      this.openedClass = false;
      this.onDismiss.emit(this);
      this.onAnyCloseEvent.emit(this);

      if (this._modalService.getOpenedModals().length < 2) {
        this._renderer.removeClass(document.body, 'dialog-open');
      }

      setTimeout(() => {
        me.visible = false;
        me.visibleChange.emit(me.visible);
        me.overlayVisible = false;
        me._changeDetectorRef.markForCheck();
        me.onDismissFinished.emit(me);
        me.onAnyCloseEventFinished.emit(me);
      }, this.hideDelay);
    }
  }

  @HostListener('document:keyup', ['$event'])
  public escapeKeyboardEvent(event: KeyboardEvent) {
    if (event.keyCode === 27 && this.visible) {
      if (!this.escapable) {
        return false;
      } else {
        this.onEscape.emit(this);
        this._modalService.closeLatestModal();
      }
    }
  }

  public setData(data: any): any {
    this._data = data;
  }

  public getData(): any {
    return this._data;
  }

  /*@HostListener('window:resize')
  private targetPlacement() {
    if (!this.nsmDialog || !this.nsmContent || !this.nsmOverlay || !this.target) {
      return;
    }

    const targetElementRect = document.querySelector(this.target).getBoundingClientRect();
    const bodyRect = this.nsmOverlay.nativeElement.getBoundingClientRect();

    const nsmContentRect = this.nsmContent.nativeElement.getBoundingClientRect();
    const nsmDialogRect = this.nsmDialog.nativeElement.getBoundingClientRect();

    const marginLeft = parseInt(getComputedStyle(this.nsmContent.nativeElement).marginLeft as any, 10);
    const marginTop = parseInt(getComputedStyle(this.nsmContent.nativeElement).marginTop as any, 10);

    let offsetTop = targetElementRect.top - nsmDialogRect.top - ((nsmContentRect.height - targetElementRect.height) / 2);
    let offsetLeft = targetElementRect.left - nsmDialogRect.left - ((nsmContentRect.width - targetElementRect.width) / 2);

    if (offsetLeft + nsmDialogRect.left + nsmContentRect.width + (marginLeft * 2) > bodyRect.width) {
      offsetLeft = bodyRect.width - (nsmDialogRect.left + nsmContentRect.width) - (marginLeft * 2);
    } else if (offsetLeft + nsmDialogRect.left < 0) {
      offsetLeft = -nsmDialogRect.left;
    }

    if (offsetTop + nsmDialogRect.top + nsmContentRect.height + marginTop > bodyRect.height) {
      offsetTop = bodyRect.height - (nsmDialogRect.top + nsmContentRect.height) - marginTop;

      if (offsetTop < 0) {
        offsetTop = 0;
      }
    }

    this._renderer.setStyle(this.nsmContent.nativeElement, 'top', offsetTop + 'px');
    this._renderer.setStyle(this.nsmContent.nativeElement, 'left', offsetLeft + 'px');
  }*/
}

