import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { OrderService } from '../services/order.service';
import { ToastService } from '../services/toast.service';

@Component({
  selector: 'app-tracking-search',
  templateUrl: './tracking-search.page.html',
  styleUrls: ['./tracking-search.page.scss'],
})

export class TrackingSearchPage implements OnInit {
  opts= ['', '', '', '', '', '', '', ''];
  isDeleted = false;
  constructor(
    private router: Router,
    public orderService: OrderService,
    private toastService: ToastService) { }

  ngOnInit() {
  }

  _searchTracking(event) {
    console.log(event.detail.value);
  }

  next(el) {
    if(!this.isDeleted){
    el.setFocus();
    const str = this.opts.join('');
    if(str.length === 8) {
       this.track();}
    }
    else { this.isDeleted = false;}
  }

  track() {
    const str = this.opts.join('');
    this.orderService.getOrderByCode(str).subscribe( res => {
      if(res){
        this.router.navigate(['/tracking-details/'+res.id+'/'+res.orderCode]);
      }
    },(error: any) => {
      this.toastService.presentToast('Order not found, check if the order code is correct');
    });
  }
}
