import { Component, Input, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { Order } from '../models/order';
import { OrderDetailed } from '../models/orderDetailed';
import { OrderPreviewFull } from '../models/orderPreviewFull';
import { OrderPriceAndDatePreview } from '../models/orderPriceAndDatePreview';
import { OrderService } from '../services/order.service';
import { ToastService } from '../services/toast.service';

@Component({
  selector: 'app-order-confimation-modal',
  templateUrl: './order-confimation-modal.component.html',
  styleUrls: ['./order-confimation-modal.component.scss'],
})
export class OrderConfimationModalComponent implements OnInit {
  @Input() order: OrderPreviewFull;
  constructor(private modalController: ModalController,
     private orderService: OrderService,
     private toastService: ToastService) { }

  ngOnInit() {}
  dismissModal() {
    this.modalController.dismiss(null, 'cancel');
  }
  registerOrder() {
    this.orderService.postOrder(this.order).subscribe(res => {
      if(res) {

      }
      else {
        this.toastService.presentToast('Confirmation declined. Check if delivery info is correct!');
      }
      });
  }

}
