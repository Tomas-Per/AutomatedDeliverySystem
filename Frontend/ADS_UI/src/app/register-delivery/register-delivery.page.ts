import { Component, OnInit, Sanitizer } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { AddressModalComponent } from '../address-modal/address-modal.component';
import { Address } from '../models/address';
import { Order } from '../models/order';
import { Size } from '../models/size';
import { OrderConfimationModalComponent } from '../order-confimation-modal/order-confimation-modal.component';


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
  addresses = [new Address(), new Address()];

  constructor(private modalController: ModalController) { }

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
    const modal = await this.modalController.create({
      component: OrderConfimationModalComponent,
      componentProps: {
        order: this.order }
    });
    await modal.present();

    await modal.onWillDismiss();
  }

}
