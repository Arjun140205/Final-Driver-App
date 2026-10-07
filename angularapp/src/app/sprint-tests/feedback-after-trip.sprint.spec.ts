import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CustomerviewrequestedComponent } from '../components/customerviewrequested/customerviewrequested.component';

describe('Feedback after trip completion sprint regression', () => {
  let component: CustomerviewrequestedComponent;
  let fixture: ComponentFixture<CustomerviewrequestedComponent>;
  let http: HttpTestingController;
  const driver = { driverId: 3, driverName: 'Asha Driver', hourlyRate: 200 };

  beforeEach(() => {
    localStorage.setItem('userId', '2');
    TestBed.configureTestingModule({
      imports: [FormsModule, RouterTestingModule, HttpClientTestingModule],
      declarations: [CustomerviewrequestedComponent]
    });
    fixture = TestBed.createComponent(CustomerviewrequestedComponent);
    component = fixture.componentInstance;
    http = TestBed.inject(HttpTestingController);
    fixture.detectChanges();
    http.expectOne('http://localhost:8080/api/driverRequest/user/2').flush([
      { driverRequestId: 1, status: 'Approved', driver },
      { driverRequestId: 2, status: 'Trip End', driver },
      { driverRequestId: 3, status: 'Closed', driver }
    ]);
    fixture.detectChanges();
  });

  afterEach(() => {
    http.verify();
    localStorage.clear();
  });

  it('keeps active trips without feedback action and exposes feedback after Trip End/Closed', () => {
    const element = fixture.nativeElement as HTMLElement;
    expect(element.querySelector('#writeReview-1')).toBeNull();
    expect(element.querySelector('#writeReview-2')).not.toBeNull();
    expect(element.querySelector('#writeReview-3')).not.toBeNull();
  });

  it('navigates to the existing feedback form with the completed trip driver', () => {
    const navigate = spyOn(TestBed.inject(Router), 'navigate').and.returnValue(Promise.resolve(true));
    component.writeReviewFor(component.requests[1]);
    expect(navigate).toHaveBeenCalledWith(['/customerpostfeedback'], { queryParams: { driverId: 3 } });
  });
});
