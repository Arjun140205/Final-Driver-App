import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, convertToParamMap, Router } from '@angular/router';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { DriverManagementComponent } from '../components/driver-management/driver-management.component';

describe('Driver success navigation sprint regression', () => {
  let component: DriverManagementComponent;
  let fixture: ComponentFixture<DriverManagementComponent>;
  let http: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, RouterTestingModule, HttpClientTestingModule],
      declarations: [DriverManagementComponent],
      providers: [{ provide: ActivatedRoute, useValue: { snapshot: { paramMap: convertToParamMap({}) } } }]
    });
    fixture = TestBed.createComponent(DriverManagementComponent);
    component = fixture.componentInstance;
    http = TestBed.inject(HttpTestingController);
    fixture.detectChanges();
  });

  afterEach(() => http.verify());

  it('routes to the existing View Drivers page when OK closes the add success popup', () => {
    const router = TestBed.inject(Router);
    const navigate = spyOn(router, 'navigate').and.returnValue(Promise.resolve(true));
    component.driverForm.setValue({
      driverName: 'Asha Driver', licenseNumber: 'DL-2026-1234', experienceYears: 5,
      contactNumber: '9876543210', vehicleType: 'Sedan', hourlyRate: 250, address: 'Bengaluru'
    });
    component.imageBase64 = 'data:image/png;base64,AA==';
    component.onSubmit();
    http.expectOne('http://localhost:8080/api/driver').flush({ driverId: 10 }, { status: 201, statusText: 'Created' });

    component.closeSuccessPopup();

    expect(navigate).toHaveBeenCalledWith(['/admin-view-drivers']);
    expect(component.driverForm.value.driverName).toBe('');
  });
});
