# DESIGN PATTERNS PROJECT

Java style guide from: https://google.github.io/styleguide/javaguide.html

## Background
In this project I modified an existing codebase to solve 5 identified issues using appropriate
design patterns and refactoring methods. Modifications were only acceptable 
within the specified scope of the following package: au.edu.sydney.cpa.erp.feaa. The details of 
the issues and modifications are described below. <br><br>
NOTE: Comments in the code briefly describe the location of modifications 
and added methods.



### Issue 1: Fixing RAM usage.
The system previously used a lot of RAM due to the Report class, which 
stores a lot of data. <br><br>
**Pattern used to solve issue:** Flyweight <br>
**Classes added:**
- ReportData

**Classes modified:**
- ReportImpl

### Issue 2: Reducing class load of Orders.
Previously, new classes were created for each type of order (based on 
work type, priority, scheduled/non-scheduled), which creates combinations 
 and the classes grow exponentially with any new type introduced.<br><br>
**Pattern used to solve issue:** Bridge <br>
**Classes removed:**
- CriticalAuditOrder 
- CriticalAuditOrderScheduled
- FirstOrderType
- FirstOrderTypeScheduled
- NewOrderImpl
- NewOrderImplScheduled
- Order66
- Order66Scheduled <br>

**Classes added:**
- OrderNonScheduled
- OrderScheduled
- OrderPriority (Interface)
  - CriticalPriority
  - NormalPriority
- OrderType (Interface)
  - Audit
  - RegularAccounting
  
 **Classes modified:**
 - FEAAFacade
   - createOrder()

### Issue 3: Streamlining client contact methods.
The previous handling of client contact methods was bulky, and needed 
to be streamlined. <br><br>
**Pattern used to solve issue:** Chain of Responsibility <br>
**Classes added:**
- CarrierPidgeonHandler
- EmailHandler
- InternalAccountingHandler
- MailHandler
- PhoneCallHandler
- SMSHandler

**Classes modified:** <br>
- ContactHandler
- ContactMethod (from Enum to Interface)
- FEAAFacade
  - finaliseOrder()

### Issue 4: Lag when loading clients.
The system previously lagged when loading clients through the CLI, due to 
the large amount of data pulled from the database at once.<br><br>
**Pattern used to solve issue:** Lazy Initialisation <br>
**Classes modified:** <br>
ClientImpl class only.

### Issue 5: Simpler process for comparing Report objects.
Any time reports needed to be compared for equality, many fields needed to be 
checked. <br><br>
**Pattern used to solve issue:** Value Object <br>
**Classes modified:** <br>
- ReportImpl
- OrderNonScheduled
  - setReport()
  - getReportEmployeeCount()

  
