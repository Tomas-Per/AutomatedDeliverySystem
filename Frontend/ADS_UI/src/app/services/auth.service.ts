import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { StorageService } from './storage.service';
import { AuthConstants } from '../auth-constants';
import { LoggedInModel } from '../models/logged-in';
import { LogInModel } from '../models/log-in';
import { RegisterModel } from '../models/register';
import { environment } from '../../environments/environment';

@Injectable({
providedIn: 'root'
})
export class AuthService {

  constructor(
      private http: HttpClient,
      private storageService: StorageService,
      private router: Router
    ) {}

  login(postData: LogInModel): Observable<LoggedInModel> {
    // eslint-disable-next-line @typescript-eslint/naming-convention
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    const options = { headers, withCredintials: false };
    const url = environment.apiUrl + 'user/login';

    return this.http.post<LoggedInModel>(url, JSON.stringify(postData), options);
  }

  signup(postData: RegisterModel): Observable<LoggedInModel> {
    // eslint-disable-next-line @typescript-eslint/naming-convention
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    const options = { headers, withCredintials: false };
    const url = environment.apiUrl + 'user/register';

    return this.http.post<LoggedInModel>(url, JSON.stringify(postData), options);
  }

  logout() {
      this.storageService.clear();
      this.router.navigate(['/login']);
  }
}
