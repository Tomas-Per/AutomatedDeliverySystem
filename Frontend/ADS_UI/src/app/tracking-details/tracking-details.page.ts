import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalController } from '@ionic/angular';
import { ChangeTimeModalComponent } from '../change-time-modal/change-time-modal.component';
import { OrderDetailed } from '../models/orderDetailed';
import { OrderService } from '../services/order.service';

@Component({
  selector: 'app-tracking-details',
  templateUrl: './tracking-details.page.html',
  styleUrls: ['./tracking-details.page.scss'],
})
export class TrackingDetailsPage implements OnInit {

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
        this.order = data;
        this.order.estimatedArrivalTime = this.datePipe.transform(this.order.estimatedArrivalTime, 'yyyy-MM-dd');
        this.order.date = this.datePipe.transform(this.order.date, 'yyyy-MM-dd');
        this.orderService.getOrderInfo(id).subscribe(info => {
            this.orderService.orderStatus = info.orderStatus;
        });
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

    const {data :newData, role} = await modal.onWillDismiss();
    if (role === 'updated') {
      const id = this.activatedRoute.snapshot.paramMap.get('id');

      this.orderService.getOrder(id)
        .subscribe(data => {
          this.order = data;
          this.order.estimatedArrivalTime = this.datePipe.transform(this.order.estimatedArrivalTime, 'yyyy-MM-dd');
          this.order.date = this.datePipe.transform(this.order.date, 'yyyy-MM-dd');
        });
    }

  }

}
