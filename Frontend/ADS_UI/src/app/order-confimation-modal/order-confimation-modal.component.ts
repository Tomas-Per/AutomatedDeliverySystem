import { Component, Input, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { Order } from '../models/order';

@Component({
  selector: 'app-order-confimation-modal',
  templateUrl: './order-confimation-modal.component.html',
  styleUrls: ['./order-confimation-modal.component.scss'],
})
export class OrderConfimationModalComponent implements OnInit {
  @Input() order: Order;
  constructor(private modalController: ModalController) { }

  ngOnInit() {}
  dismissModal() {
    this.modalController.dismiss(null, 'cancel');
  }

}
