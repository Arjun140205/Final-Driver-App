import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { User } from '../../models/user.model';
import { EMAIL_REGEX, MOBILE_REGEX } from '../../utils/constants';

function passwordsMatch(group: AbstractControl): ValidationErrors | null {
  const password = group.get('password')?.value;
  const confirm = group.get('confirmPassword')?.value;
  return confirm && password !== confirm ? { mismatch: true } : null;
}

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  signupForm: FormGroup;
  submitted: boolean = false;
  errorMessage: string = '';
  showSuccessPopup: boolean = false;

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
    this.signupForm = this.fb.group(
      {
        username: ['', [Validators.required]],
        email: ['', [Validators.required, Validators.pattern(EMAIL_REGEX)]],
        mobileNumber: ['', [Validators.required, Validators.pattern(MOBILE_REGEX)]],
        password: ['', [Validators.required, Validators.minLength(6)]],
        confirmPassword: ['', [Validators.required]],
        userRole: ['', [Validators.required]]
      },
      { validators: passwordsMatch }
    );
  }

  get f() {
    return this.signupForm.controls;
  }

  isInvalid(field: string): boolean {
    const control = this.signupForm.get(field)!;
    return control.invalid && (control.touched || this.submitted);
  }

  get passwordMismatch(): boolean {
    return !!this.signupForm.errors?.['mismatch'] && !this.f['confirmPassword'].errors?.['required'];
  }

  onSubmit(): void {
    this.submitted = true;
    this.errorMessage = '';
    if (this.signupForm.invalid) {
      return;
    }
    const value = this.signupForm.value;
    const user: User = {
      username: value.username.trim(),
      email: value.email.trim(),
      mobileNumber: value.mobileNumber,
      password: value.password,
      userRole: value.userRole
    };
    this.authService.register(user).subscribe({
      next: () => (this.showSuccessPopup = true),
      error: (err) => {
        if (err && err.status === 409) {
          this.errorMessage = 'A user with this email already exists';
        } else if (err && err.status >= 400 && err.status < 500) {
          this.errorMessage = typeof err.error === 'string' && err.error ? err.error : 'Registration failed. Please try again.';
        }
      }
    });
  }

  goToLogin(): void {
    this.showSuccessPopup = false;
    this.router.navigate(['/login']);
  }
}
