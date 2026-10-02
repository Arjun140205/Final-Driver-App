import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { apiUrl } from '../../apiconfig';
import { User } from '../models/user.model';
import { Login } from '../models/login.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  public apiUrl: string = apiUrl;

  private userRoleSubject = new BehaviorSubject<string | null>(this.readStored('userRole'));
  private userIdSubject = new BehaviorSubject<number | null>(this.toNumber(this.readStored('userId')));
  private usernameSubject = new BehaviorSubject<string | null>(this.readStored('username'));

  userRole$: Observable<string | null> = this.userRoleSubject.asObservable();
  userId$: Observable<number | null> = this.userIdSubject.asObservable();
  username$: Observable<string | null> = this.usernameSubject.asObservable();

  constructor(private http: HttpClient) {}

  register(user: User): Observable<any> {
    return this.http.post(`${this.apiUrl}/api/register`, user);
  }

  login(login: Login): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/api/login`, login).pipe(
      tap((response) => {
        if (response && response.token) {
          localStorage.setItem('token', response.token);
          localStorage.setItem('userRole', response.userRole);
          localStorage.setItem('userId', String(response.userId));
          localStorage.setItem('username', response.username);
          this.userRoleSubject.next(response.userRole);
          this.userIdSubject.next(Number(response.userId));
          this.usernameSubject.next(response.username);
        }
      })
    );
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('userRole');
    localStorage.removeItem('userId');
    localStorage.removeItem('username');
    this.userRoleSubject.next(null);
    this.userIdSubject.next(null);
    this.usernameSubject.next(null);
  }

  isLoggedIn(): boolean {
    const token = this.getToken();
    if (!token) {
      return false;
    }
    if (this.isTokenExpired(token)) {
      this.logout();
      return false;
    }
    return true;
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getUserRole(): string | null {
    return localStorage.getItem('userRole');
  }

  getUserId(): number | null {
    return this.toNumber(localStorage.getItem('userId'));
  }

  getUsername(): string | null {
    return localStorage.getItem('username');
  }

  private isTokenExpired(token: string): boolean {
    try {
      const payload = JSON.parse(atob(token.split('.')[1].replace(/-/g, '+').replace(/_/g, '/')));
      return !!payload.exp && payload.exp * 1000 < Date.now();
    } catch {
      // A token we cannot decode is left for the server to judge.
      return false;
    }
  }

  private readStored(key: string): string | null {
    try {
      return localStorage.getItem(key);
    } catch {
      return null;
    }
  }

  private toNumber(value: string | null): number | null {
    if (value === null || value === '' || isNaN(Number(value))) {
      return null;
    }
    return Number(value);
  }
}
