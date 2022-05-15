import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { OrderDetailed } from '../models/orderDetailed';
import { OrderService } from '../services/order.service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-order-details',
  templateUrl: './order-details.page.html',
  styleUrls: ['./order-details.page.scss'],
})
export class OrderDetailsPage implements OnInit {

  order: OrderDetailed = null;
  constructor(private orderService: OrderService,
    private activatedRoute: ActivatedRoute,
    private datePipe: DatePipe
    ) { }

  ngOnInit() {
    const id = this.activatedRoute.snapshot.paramMap.get('id');

    this.orderService.getOrder(id)
      .subscribe(data => {
        this.order = data;
        this.order.estimatedArrivalTime = this.datePipe.transform(this.order.estimatedArrivalTime, 'yyyy-M-d, h:mm a');
        this.order.date = this.datePipe.transform(this.order.date, 'yyyy-M-d');
        console.log(data);
      });
  }

}
