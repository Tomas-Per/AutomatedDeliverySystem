import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { AddressModalComponent } from '../address-modal/address-modal.component';
import { Address } from '../models/address';
import { AddressPreview } from '../models/addressPreview';
import { Order } from '../models/order';
import { OrderPreviewFull } from '../models/orderPreviewFull';
import { OrderPriceAndDatePreview } from '../models/orderPriceAndDatePreview';
import { Size } from '../models/size';
import { User } from '../models/user';
import { OrderConfimationModalComponent } from '../order-confimation-modal/order-confimation-modal.component';
import { AuthService } from '../services/auth.service';
import { OrderService } from '../services/order.service';
import { StorageService } from '../services/storage.service';


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
  standardOrder = new OrderPreviewFull();
  expressOrder = new OrderPreviewFull();

  constructor(private modalController: ModalController,
    public orderService: OrderService,
    public authService: AuthService,
    private storageService: StorageService) { }

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
    this.addresses[0].address.street ='';
    this.addresses[1].address.street ='';
    this.expressOrder.estimatedArrivalTime = '';
    this.standardOrder.estimatedArrivalTime = '';

    if(this.authService.isLoggedIn){
      // get package previews
      this.storageService.get('userData').then((data)=>{
        this.addresses[0].user.firstName = data.firstName;
        this.addresses[0].user.lastName = data.lastName;
        this.addresses[0].user.phoneNumber = data.phoneNumber;
      });
    }
  }
  calculatePriceAndDate() {
    this.orderPreview.size = this.size;
    // eslint-disable-next-line prefer-const
    let standardPreview = this.orderPreview;
    standardPreview.isExpress = false;
    console.log(standardPreview);
    this.orderService.getOrderPreview(standardPreview).subscribe(res => {
      this.standardOrder.price = res.price;
      this.standardOrder.estimatedArrivalTime = res.estimatedArrivalTime;
      });
    // eslint-disable-next-line prefer-const
    let expressPreview = this.orderPreview;
    expressPreview.isExpress = true;
    console.log(expressPreview);
    this.orderService.getOrderPreview(expressPreview).subscribe(res2 => {
      this.expressOrder.price = res2.price;
      this.expressOrder.estimatedArrivalTime = res2.estimatedArrivalTime;
      });
  }
  async registerOrder() {
    let confirmingOrder = new OrderPreviewFull();
    if (this.isExpress) {
      confirmingOrder = this.expressOrder;}
    else {
      confirmingOrder = this.standardOrder;}

    confirmingOrder.destinationAddress = this.destinationAddress;
    confirmingOrder.sourceAddress = this.sourceAddress;
    confirmingOrder.destinationUser = this.destinationUser;
    confirmingOrder.sourceUser = this.sourceUser;
    confirmingOrder.isExpress = this.isExpress;
    confirmingOrder.isFragile = this.isFragile;
    confirmingOrder.size = this.size;

    const modal = await this.modalController.create({
      component: OrderConfimationModalComponent,
      componentProps: {
        order: confirmingOrder }
    });
    await modal.present();

    await modal.onWillDismiss();
  }

}
