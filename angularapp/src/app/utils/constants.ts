export const VEHICLE_TYPES: string[] = ['Sedan', 'SUV', 'Hatchback', 'Truck', 'Van', 'Bike'];

export const DRIVER_STATUSES: string[] = ['Active', 'Inactive', 'On Leave'];

export const REQUEST_STATUSES: string[] = ['Pending', 'Approved', 'Rejected', 'Trip End', 'Closed'];

/** Stages shown in the "Request Progression" modal (a rejected request stops after "Pending"). */
export const REQUEST_STAGES: string[] = ['Pending', 'Approved', 'Trip End', 'Closed'];

export const FEEDBACK_CATEGORIES: string[] = [
  'Driver Performance',
  'Service Experience',
  'Punctuality',
  'Vehicle Condition',
  'Other'
];

export const SENTIMENTS: string[] = ['Positive', 'Neutral', 'Negative'];

export const EMAIL_REGEX = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
export const MOBILE_REGEX = /^\d{10}$/;
