import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { StorageService } from '../services/storage.service';
import { OrderService } from '../services/order.service';
import { OrderPreview } from '../models/orderPreview';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage implements OnInit{

  packages: OrderPreview[];
  email: string;

  constructor(private router: Router,
    private storageService: StorageService,
    public orderService: OrderService,
    public authService: AuthService
    ) {}

  ngOnInit() {
    if(this.authService.isLoggedIn){
      // get package previews
      this.storageService.get('email').then((email)=>{
        this.email = email;
        this.orderService.getOrderList(this.email)
          .subscribe(orders => {
            this.packages = orders;
            this.packages.forEach(order => {
              if(order.orderStatus === 'IN_DELIVERY') {
                order.orderStatus = 'In delivery';
              } else if (order.orderStatus === 'ARRIVED') {
                order.orderStatus = 'Arrived';
              } else {
                order.orderStatus = 'Waiting for courier';
              };
            });
          });

      });
    }

  }
  navigateToDeliveryTracking() {
    this.router.navigate(['/tracking-search/']);
  }
  navigateToDeliveryRegister() {
    this.router.navigate(['/register-delivery']);
  }

  logOut() {
    this.authService.logout();
  }

}
