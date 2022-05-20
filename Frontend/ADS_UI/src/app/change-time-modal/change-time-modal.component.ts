import { Component, Input, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { EditOrder } from '../models/editOrder';
import { OrderDetailed } from '../models/orderDetailed';
import { OrderService } from '../services/order.service';

@Component({
  selector: 'app-change-time-modal',
  templateUrl: './change-time-modal.component.html',
  styleUrls: ['./change-time-modal.component.scss'],
})
export class ChangeTimeModalComponent implements OnInit {
  editableDate = new EditOrder();
  orderDetailed = new OrderDetailed();

  // eslint-disable-next-line @typescript-eslint/member-ordering
  @Input() id: string;
  // eslint-disable-next-line @typescript-eslint/member-ordering
  @Input() estimatedDate: string;

  constructor(private modalController: ModalController,
    private orderService: OrderService) { }

  ngOnInit() {
    console.log(this.estimatedDate);
    // this.estimatedDate.setHours(0);
    // this.estimatedDate.setMinutes(0);
    // this.estimatedDate.setSeconds(0);
    // this.estimatedDate.setMilliseconds(0);
  }

  dismissModal() {
    this.modalController.dismiss(null, 'closed');
  }
  saveTime() {
    let dateFrom = new Date(this.estimatedDate).setHours(0,0,0);
    let dateTo = new Date(this.estimatedDate).setHours(0,0,0);
    const hoursFrom = new Date(this.editableDate.convenientArrivalTimeFrom).getHours();
    const hoursTo = new Date(this.editableDate.convenientArrivalTimeTo).getHours();
    const minutesFrom = new Date(this.editableDate.convenientArrivalTimeFrom).getMinutes();
    const minutesTo = new Date(this.editableDate.convenientArrivalTimeTo).getMinutes();
    dateFrom = new Date(dateFrom).setHours(hoursFrom,minutesFrom);
    dateTo = new Date(dateTo).setHours(hoursTo,minutesTo);

    this.editableDate.convenientArrivalTimeFrom = JSON.stringify(dateFrom);
    this.editableDate.convenientArrivalTimeTo = JSON.stringify(dateTo);
    this.orderService.editOrder(this.id, this.editableDate).subscribe(res =>
     {this.orderDetailed = res;});
    this.modalController.dismiss(this.orderDetailed, 'updated');
  }

}
