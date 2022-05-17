import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { AddressModalComponent } from '../address-modal/address-modal.component';
import { Address } from '../models/address';
import { AddressPreview } from '../models/addressPreview';
import { Order } from '../models/order';
import { OrderPriceAndDatePreview } from '../models/orderPriceAndDatePreview';
import { Size } from '../models/size';
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
  addresses = [new Address(), new Address()];

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
    console.log(this.addresses);
  }

  ngOnInit() {
    this.addresses[0] = {role: 'Sender',
      firstName: '',
      lastName: '',
      phoneNumber: '',
      street: '',
      houseNumber: '',
      country: '',
      city: '',
      postalCode: null};

    this.addresses[1]={role: 'Receiver',
      firstName: '',
      lastName: '',
      phoneNumber: '',
      street: '',
      houseNumber: '',
      country: '',
      city: '',
      postalCode: null};
      this.isFragile = false;
      this.isExpress = false;
      this.size = 0;
  }
  async registerOrder() {
    this.order = {
      sender: this.addresses[0],
      receiver: this.addresses[1],
      isFragile: this.isFragile,
      size: this.size,
      isExpress: this.isExpress
    };
    this.sourceAddress = {
      city: this.addresses[0].city,
      street: this.addresses[0].street,
      houseNumber: this.addresses[0].houseNumber,
      country: this.addresses[0].country,
      postalCode: this.addresses[0].postalCode
    };
    this.destinationAddress = {
        city: this.addresses[1].city,
        street: this.addresses[1].street,
        houseNumber: this.addresses[1].houseNumber,
        country: this.addresses[1].country,
        postalCode: this.addresses[1].postalCode
    };
    this.orderPreview = {
      sourceAddress: this.sourceAddress,
      destinationAddress: this.destinationAddress,
      isExpress: this.isExpress,
      isFragile: this.isFragile,
      size: this.size
    };
    this.orderService.getOrderPreview(this.orderPreview).subscribe(res => {console.log(res);});
    const modal = await this.modalController.create({
      component: OrderConfimationModalComponent,
      componentProps: {
        order: this.order }
    });
    await modal.present();

    await modal.onWillDismiss();
  }

}
