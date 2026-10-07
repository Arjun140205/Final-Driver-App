import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { DriverService } from '../services/driver.service';

describe('Driver API contract sprint regression', () => {
  let service: DriverService;
  let http: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({ imports: [HttpClientTestingModule] });
    service = TestBed.inject(DriverService);
    http = TestBed.inject(HttpTestingController);
  });

  afterEach(() => http.verify());

  it('keeps the existing create route, method, and driver field names', () => {
    const payload = {
      driverName: 'Asha Driver', licenseNumber: 'DL-2026-1234', experienceYears: 5,
      contactNumber: '9876543210', availabilityStatus: 'Active', address: 'Bengaluru',
      vehicleType: 'Sedan', hourlyRate: 250, image: 'data:image/png;base64,AA=='
    };
    service.addDriver(payload).subscribe((response) => expect(response.driverId).toBe(10));
    const request = http.expectOne('http://localhost:8080/api/driver');
    expect(request.request.method).toBe('POST');
    expect(request.request.body).toEqual(payload);
    request.flush({ ...payload, driverId: 10 }, { status: 201, statusText: 'Created' });
  });
});
