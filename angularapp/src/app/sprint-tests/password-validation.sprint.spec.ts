import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { SignupComponent } from '../components/signup/signup.component';

describe('Signup password validation sprint regression', () => {
  let component: SignupComponent;
  let fixture: ComponentFixture<SignupComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, HttpClientTestingModule, RouterTestingModule],
      declarations: [SignupComponent]
    });
    fixture = TestBed.createComponent(SignupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('keeps the existing six-character policy and communicates it in the form', () => {
    component.signupForm.get('password')?.setValue('abc12');
    expect(component.signupForm.get('password')?.invalid).toBeTrue();
    component.signupForm.get('password')?.setValue('abc123');
    fixture.detectChanges();
    expect(component.signupForm.get('password')?.valid).toBeTrue();
    expect((fixture.nativeElement as HTMLElement).textContent).toContain('At least 6 characters');
    expect((fixture.nativeElement as HTMLElement).querySelector('.requirement-met')).not.toBeNull();
  });

  it('rejects a whitespace-only username', () => {
    component.signupForm.get('username')?.setValue('   ');
    expect(component.signupForm.get('username')?.invalid).toBeTrue();
  });
});
