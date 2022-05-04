import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { AddressModalComponent } from '../address-modal/address-modal.component';

@Component({
  selector: 'app-register-delivery',
  templateUrl: './register-delivery.page.html',
  styleUrls: ['./register-delivery.page.scss'],
})
export class RegisterDeliveryPage implements OnInit {
  isExpress: boolean;
  addresses = [
    {
      role: 'Sender',
      firstName: '',
      lastName: '',
      phoneNumber: '',
      street: '',
      houseNumber: '',
      country: '',
      city: '',
      postalCode: null
    },
    {
      role: 'Reciever',
      firstName: '',
      lastName: '',
      phoneNumber: '',
      street: '',
      houseNumber: '',
      country: '',
      city: '',
      postalCode: null
    }
  ];
  constructor(private modalController: ModalController) { }

  async openAddressModal(address) {
    console.log(address);
    const modal = await this.modalController.create({
      component: AddressModalComponent,
      componentProps: {
        role: address.role,
        firstName: address.firstName,
        lastName: address.lastName,
        phoneNumber: address.phoneNumber,
        street: address.street,
        houseNumber: address.houseNumber,
        country: address.country,
        city: address.city,
        postalCode: address.postalCode }
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
    console.log(this.addresses);
  }

}
