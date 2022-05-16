import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { OrderPreview } from '../models/orderPreview';
import { OrderDetailed } from '../models/orderDetailed';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  orderStatus: string;

  constructor(
    private http: HttpClient
  ) { }

  getOrderList(email: string): Observable<OrderPreview[]> {
    // eslint-disable-next-line @typescript-eslint/naming-convention
    const headers = new HttpHeaders({'Content-Type': 'application/json'});

    const params = new HttpParams().set('email', email);
    const options = { headers, withCredintials: false, params};
    const url = environment.apiUrl + 'api/order/email';

    return this.http.get<OrderPreview[]>(url, options);
  }

  getOrder(id: string): Observable<OrderDetailed> {
    // eslint-disable-next-line @typescript-eslint/naming-convention
    const headers = new HttpHeaders({'Content-Type': 'application/json'});

    const options = { headers, withCredintials: false};
    const url = environment.apiUrl + 'api/order/' + id;

    return this.http.get<OrderDetailed>(url, options);
  }
}
