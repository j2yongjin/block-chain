import { Injectable } from '@angular/core';
import { ModalComponent } from './modal.component';
import { ModalInstance } from './modal-instance';

@Injectable()
export class ModalService {
  public modalStack: ModalInstance[] = [];
  private debouncer: any;

  public addModal(modalInstance: ModalInstance, force?: boolean): void {
    if (force) {
      const i: number = this.modalStack.findIndex((o: ModalInstance) => {
        return o.id === modalInstance.id;
      });
      if (i > -1) {
        this.modalStack[i].modal = modalInstance.modal;
      } else {
        this.modalStack.push(modalInstance);
      }
      return;
    }
    this.modalStack.push(modalInstance);
  }

  public getModal(id: string): ModalComponent {
    return this.modalStack.filter((o: any) => {
      return o.id === id;
    })[0].modal;
  }

  public open(id: string, force = false): void {
    const instance = this.modalStack.find((o: ModalInstance) => {
      return o.id === id;
    });
    if (!!instance) {
      instance.modal.open(force);
    } else {
      throw new Error('Modal not found');
    }
  }

  public close(id: string): void {
    const instance = this.modalStack.find((o: ModalInstance) => {
      return o.id === id;
    });
    if (!!instance) {
      instance.modal.close();
    } else {
      throw new Error('Modal not found');
    }
  }

  public getModalStack(): ModalInstance[] {
    return this.modalStack;
  }

  public getOpenedModals(): ModalInstance[] {
    const modals: ModalInstance[] = [];
    this.modalStack.forEach((o: ModalInstance) => {
      if (o.modal.visible) {
        modals.push(o);
      }
    });
    return modals;
  }

  public getHigherIndex(): number {
    const index: number[] = [1041];
    const modals: ModalInstance[] = this.getModalStack();
    modals.forEach((o: ModalInstance) => {
      index.push(o.modal.layerPosition);
    });
    return Math.max(...index) + 1;
  }

  public getModalStackCount(): number {
    return this.modalStack.length;
  }

  public removeModal(id: string): void {
    const i: number = this.modalStack.findIndex((o: any) => {
      return o.id === id;
    });
    if (i > -1) {
      this.modalStack.splice(i, 1);
    }
  }

  public closeLatestModal(): void {
    const me = this;
    clearTimeout(this.debouncer);
    this.debouncer = setTimeout(() => {
      let tmp: ModalInstance | undefined;
      me.getOpenedModals().forEach((m: ModalInstance) => {
        if (m.modal.layerPosition > (!!tmp ? tmp.modal.layerPosition : 0 && m.modal.escapable)) {
          tmp = m;
        }
      });
      return !!tmp ? tmp.modal.close() : false;
    }, 100);
  }
}
