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

  @Input() id: string;
  @Input() estimatedDate: string;

  editableDate = new EditOrder();
  orderDetailed = new OrderDetailed();

  constructor(private modalController: ModalController,
    private orderService: OrderService) { }

  ngOnInit() {
    const initialDate = new Date();
    const tzoffset = (new Date()).getTimezoneOffset() * 60000;
    const localISOTime = (new Date(initialDate.setHours(8, 0) - tzoffset)).toISOString().slice(0, -1);
    this.editableDate.convenientArrivalTimeFrom = localISOTime;
    this.editableDate.convenientArrivalTimeTo = localISOTime;
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
    dateFrom = new Date(dateFrom).setHours(hoursFrom, minutesFrom);
    dateTo = new Date(dateTo).setHours(hoursTo, minutesTo);

    this.editableDate.convenientArrivalTimeFrom = JSON.stringify(dateFrom);
    this.editableDate.convenientArrivalTimeTo = JSON.stringify(dateTo);
    this.editableDate.optLockVersion = this.orderService.orderOptLockVersion;
    this.orderService.editOrder(this.id, this.editableDate).subscribe(res =>
     {this.orderDetailed = res;});
    this.modalController.dismiss(this.orderDetailed, 'updated');
  }

}
