import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { AddressModalComponent } from '../address-modal/address-modal.component';
import { Address } from '../models/address';
import { AddressPreview } from '../models/addressPreview';
import { Order } from '../models/order';
import { OrderDetailed } from '../models/orderDetailed';
import { OrderPriceAndDatePreview } from '../models/orderPriceAndDatePreview';
import { Size } from '../models/size';
import { User } from '../models/user';
import { OrderConfimationModalComponent } from '../order-confimation-modal/order-confimation-modal.component';
import { OrderService } from '../services/order.service';


@Component({
  selector: 'app-register-delivery',
  templateUrl: './register-delivery.page.html',
  styleUrls: ['./register-delivery.page.scss'],
})
export class RegisterDeliveryPage implements OnInit {
  isExpress: boolean;
  isFragile: boolean;
  size: Size;
  order = new Order();
  orderPreview = new OrderPriceAndDatePreview();
  sourceAddress = new AddressPreview();
  destinationAddress = new AddressPreview();
  sourceUser = new User();
  destinationUser = new User();
  addresses = [new Address(), new Address()];
  standartOrder = new OrderDetailed();
  expressOrder = new OrderDetailed();

  constructor(private modalController: ModalController,
    public orderService: OrderService) { }

  async openAddressModal(address) {
    const modal = await this.modalController.create({
      component: AddressModalComponent,
      componentProps: {
        address }
    });

    await modal.present();

    const {data :newData, role} = await modal.onWillDismiss();
    if (role === 'saved') {
      const index = this.addresses.findIndex(add => add.role === address.role);
      this.addresses[index] = newData;
    }
    this.sourceAddress = this.addresses[0].address;
    this.destinationAddress = this.addresses[1].address;
    this.sourceUser = this.addresses[0].user;
    this.destinationUser = this.addresses[1].user;
    this.orderPreview = {
      sourceAddress: this.sourceAddress,
      destinationAddress: this.destinationAddress,
      isExpress: this.isExpress,
      isFragile: this.isFragile,
      size: this.size,
      sourceUser: this.sourceUser,
      destinationUser: this.destinationUser
    };
  }

  ngOnInit() {
    this.addresses[0] = {role: 'Sender',
      user: this.sourceUser,
      address: this.sourceAddress};

    this.addresses[1]={role: 'Receiver',
      user: this.destinationUser,
      address: this.destinationAddress};

    this.isFragile = false;
    this.isExpress = false;
    this.size = 0;
    this.addresses[0].user.firstName ='';
    this.addresses[1].user.firstName ='';
  }
  calculatePriceAndDate() {
    const standartPreview = this.orderPreview;
    const expressPreview = this.orderPreview;
    standartPreview.isExpress = false;
    expressPreview.isExpress = true;
    this.orderService.getOrderPreview(standartPreview).subscribe(res => {
      this.standartOrder.price = res.price;
      this.standartOrder.estimatedArrivalTime = res.estimatedArrivalTime;
      });
    this.orderService.getOrderPreview(expressPreview).subscribe(res2 => {
      this.expressOrder.price = res2.price;
      this.expressOrder.estimatedArrivalTime = res2.estimatedArrivalTime;
      });
  }
  async registerOrder() {
    this.order = {
      sender: this.addresses[0],
      receiver: this.addresses[1],
      isFragile: this.isFragile,
      size: this.size,
      isExpress: this.isExpress
    };


    const modal = await this.modalController.create({
      component: OrderConfimationModalComponent,
      componentProps: {
        order: this.order }
    });
    await modal.present();

    await modal.onWillDismiss();
  }

}
