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
      this.storageService.get('email').then((data)=>{
        console.log(data);
        this.email = data;
        this.orderService.getOrderList(this.email)
          .subscribe(data1 => {
            this.packages = data1;
            this.packages.forEach(element => {
              if(element.orderStatus === 'IN_DELIVERY') {
                element.orderStatus = 'In delivery';
              } else if (element.orderStatus === 'ARRIVED') {
                  element.orderStatus = 'Arrived';
              } else {
                  element.orderStatus = 'Waiting for courier';
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
