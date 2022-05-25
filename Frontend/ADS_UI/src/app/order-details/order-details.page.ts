import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { OrderDetailed } from '../models/orderDetailed';
import { OrderService } from '../services/order.service';
import { DatePipe } from '@angular/common';
import { ModalController } from '@ionic/angular';
import { ChangeTimeModalComponent } from '../change-time-modal/change-time-modal.component';

@Component({
  selector: 'app-order-details',
  templateUrl: './order-details.page.html',
  styleUrls: ['./order-details.page.scss'],
})
export class OrderDetailsPage implements OnInit {

  order: OrderDetailed;

  constructor(public orderService: OrderService,
    private activatedRoute: ActivatedRoute,
    private datePipe: DatePipe,
    private modalController: ModalController
    ) { }

  ngOnInit() {
    const id = this.activatedRoute.snapshot.paramMap.get('id');

    this.orderService.getOrder(id)
      .subscribe(data => {
        console.log(data);
        this.order = data;
        this.order.estimatedArrivalTime = this.datePipe.transform(this.order.estimatedArrivalTime, 'yyyy-MM-dd, h:mm a');
        this.order.date = this.datePipe.transform(this.order.date, 'yyyy-MM-dd');
      });
  }

  async openTimePickerModal() {
    const modal = await this.modalController.create({
      component: ChangeTimeModalComponent,
      componentProps: {
        id: this.order.id,
        estimatedDate: this.order.estimatedArrivalTime }
    });

    await modal.present();
  }

}
