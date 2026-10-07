# Sprint Verification & Regression Report

## 1. Verification Objective

Verify that DTO/service mapping preserves existing HTTP behavior, the targeted UI changes work, and the new validation, exception, logging, and relationship behavior is covered by new tests.

## 2. Existing Tests — Baseline

No existing test source or configuration was edited. The existing backend integration suite contains 15 ordered HTTP tests. The standard Angular test run only executes existing focused `fit(...)` tests; this repository behavior was left unchanged.

## 3. New Test Files Created

Backend: `DriverMapperSprintTest`, `DriverValidationSprintTest`, `DriverServiceSprintTest`, `DriverControllerSprintTest`, `GlobalExceptionHandlerSprintTest`, `LoggingAspectSprintTest`, and `RelationshipRegressionSprintTest`.

Angular: `password-validation.sprint.spec.ts`, `feedback-after-trip.sprint.spec.ts`, `driver-navigation.sprint.spec.ts`, and `api-regression.sprint.spec.ts`.

## 4. Backend JUnit Tests

Command: `./mvnw test` from `springapp`.

Result after clean compilation: **25 tests, 0 failures, 0 errors, 0 skipped**. This includes the 15 existing HTTP integration tests and 10 new sprint tests.

## 5. Angular Karma Tests

Commands from `angularapp` with Node 20:

- `npx ng test --watch=false`: Karma ran **8 of 95**, with 8 passing and 87 skipped. Existing focused `fit(...)` specs skip the remaining tests; none of those baseline specs was changed.
- `npx ng test --watch=false --include='src/app/sprint-tests/**/*.spec.ts'`: **6 of 6 new sprint specs passed** in Chrome Headless.
- Focused affected baseline specs plus all sprint specs: **22 of 22 passed** after the clean reinstall.

## 6. Validation Test Results

Backend DTO validation tests reject missing/malformed driver fields and accept a valid driver request. A live invalid registration returned HTTP 400 with `status`, `message`, `timestamp`, `path`, and field errors for email, password, mobile number, and username. Angular sprint specs verified the current six-character password rule and whitespace-only username rejection.

## 7. DTO Test Results

Driver DTO/entity round-trip test passed. Controller DTO response test passed. The existing integration suite passed registration, driver, driver-request, and feedback request paths after services began accepting/returning DTOs.

## 8. Exception Handling Test Results

The new global exception handler test verified duplicate-driver HTTP 409, safe error response fields, and error-log service invocation. Live registration validation returned the structured HTTP 400 described above. Generic and database exception handlers compile and are part of the running Spring context; no live database-failure injection was performed.

## 9. Logging Verification

The logging advice annotation test confirms both `@Before` and `@After` advice. A live `GET /api/driver` returned `204` and emitted both start and completion lines for `DriverController.viewAllDrivers` and `DriverServiceImpl.getAllDrivers`, without method arguments.

## 10. ErrorLog Verification

The live validation request succeeded with the new global handler while the backend was connected to its `error_logs` JPA table. The backend log showed an insert into `error_logs`. Persisted rows contain sanitized summaries rather than raw exception messages.

## 11. Duplicate Driver Verification

New service tests verify a unique license is saved and an existing license throws `DuplicateDriverException` before save. The repository uses `existsByLicenseNumber`, matching the actual domain field.

## 12. Relationship Regression Verification

New regression tests assert that `DriverRequest.user`, `DriverRequest.driver`, `Feedback.user`, and `Feedback.driver` retain default no-cascade mappings. Mapper regression verifies nested user/driver summaries remain in DTOs. No relationship or cascade annotation was changed.

## 13. Feedback Form Verification

Two new Angular specs passed: active (`Approved`) requests do not expose the new review button; completed (`Trip End`/`Closed`) requests do, and selecting it navigates to `/customerpostfeedback` with the existing driver query parameter.

## 14. Driver Navigation Verification

New Angular test passed: after successful driver creation, closing the success popup navigates to `/admin-view-drivers`. The form is still reset to preserve its prior post-add cleanup behavior.

## 15. Java Stream Refactor Verification

Java compilation and all backend tests passed with stream-based entity-to-DTO list mapping in `ApiMapper`.

## 16. API Regression Testing

The existing post-refactor HTTP integration tests passed their registration/login, role checks, driver create/read/update/list, driver-request create/list, and feedback create/read/list checks. The new frontend API test passed its `POST /api/driver` method, route, and request field-name assertions.

## 17. API Before/After Comparison

The original integration tests encode the pre-sprint expected contract; a live pre-change response snapshot was not captured. The same integration suite passed after refactoring. Actual post-refactor observations:

### `POST /api/register`

Request used the existing fields `email`, `password`, `username`, `mobileNumber`, and `userRole`.

Observed: **201 Created** with body:

```json
{"userId":1,"email":"sprint-check@example.com","username":"Sprint Check","mobileNumber":"9876543210","userRole":"Customer"}
```

The password was not returned.

### `GET /api/driver`

Observed with the empty in-memory driver collection: **204 No Content**, empty body, matching the existing empty-list behavior.

### Invalid `POST /api/register`

Observed: **400 Bad Request**. Body fields were `status`, `message`, `timestamp`, `path`, and `errors`; the field-error map identified invalid email, short password, mobile format, and blank username.

### Other existing integration checks

Observed in `SpringappApplicationTests` after refactoring: login `200` and non-null token; driver create `201`; driver read/list `200`; driver update `200` and updated `driverName`/`experienceYears`; customer driver-request create `201` and customer lookup `200`; feedback create `201`, all-feedback `200`, user-feedback `200`, feedback-by-id `200`; existing role-denied calls returned `403`.

Delete endpoints and a live authenticated invalid-driver submission were not exercised in this sprint.

## 18. Commands Executed

- `source /Users/arjunbirsingh/.nvm/nvm.sh && nvm use 20 && npm i`
- `source /Users/arjunbirsingh/.nvm/nvm.sh && nvm use 20 && npm ci` (clean reinstall after detecting a corrupted esbuild binary)
- `npm run build`
- `npx ng test --watch=false`
- `npx ng test --watch=false --include='src/app/sprint-tests/**/*.spec.ts'`
- `./mvnw -DskipTests test` (compile check)
- `./mvnw test`
- `./mvnw spring-boot:run`
- `npm start`
- Local `curl` requests to `/api/register` and `/api/driver`

## 19. Test Results

`npm i` completed with “up to date” on Node `v20.20.2` / npm `10.8.2`. A clean `npm ci` restored a corrupted esbuild install. Final Angular production build passed; 22 affected baseline/sprint specs passed. The standard Angular Karma command passed its 8 focused specs and skipped 87. Spring suite passed 25/25 after clean compilation.

## 20. Existing Test Files Not Modified

Existing Angular `*.spec.ts` files: **not modified**. Existing JUnit tests: **not modified**.

## 21. Karma Configuration Not Modified

`angularapp/karma.conf.js`: **not modified**.

## 22. JUnit Configuration Not Modified

Existing JUnit and Maven test configuration: **not modified**.

## 23. Remaining Issues

- The baseline Angular suite contains focused `fit(...)` tests, so the default run skips 87 specs. They were not edited; sprint specs were run separately using `--include`.
- npm reports 73 dependency audit findings from the locked frontend dependency tree (7 low, 19 moderate, 45 high, 2 critical). No dependency upgrades were applied as part of this behavior-preserving sprint.
- Public registration still accepts a caller-provided role, and existing signup UI offers Admin. Changing this would alter the current registration/auth contract and the existing integration fixture, so it was preserved; production account provisioning should decide how admin accounts are granted.
- Existing request/feedback endpoints accept client-supplied user IDs and do not enforce record ownership in the service layer. This authorization/IDOR concern predates this sprint and needs a separate policy-aware security change.
- Live delete endpoint checks, live authenticated invalid-driver validation, and production-database migration behavior were not exercised.

## 24. Final Sprint Status

Architecture, validation, exception handling, logging, targeted UI improvements, new tests, documentation, backend integration tests, Angular build, and the six sprint Angular tests are implemented and verified. The backend and Angular dev servers are running at `http://localhost:8080` and `http://localhost:8081`.
