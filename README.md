SOFTWARE DESIGN PATTERNS


You must include a readme file (a simple text file, either .txt or .md) listing what CPA issues you have solved and where in the code you did so. If you have made any assumptions in your work, or if your code requires anything in particular (like external dependencies/libraries) you should list them here also.

Java style guide from: https://google.github.io/styleguide/javaguide.html

### 1. Fixing RAM usage.
**Pattern used:** Flyweight <br>
**Classes added:**
- ReportData

**Classes affected:**
- ReportImpl

### 2. Reducing class load of Orders.
**Pattern used:** Bridge <br>
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
  
 **Class(es) affected:**
 - FEAAFacade
   - createOrder()

### 3. Streamlining client contact methods.
**Pattern used:** Chain of Responsibility <br>
**Classes added:**
- CarrierPidgeonHandler
- EmailHandler
- InternalAccountingHandler
- MailHandler
- PhoneCallHandler
- SMSHandler

**Class(es) affected:** <br>
- ContactHandler
- ContactMethod (from Enum to Interface)
- FEAAFacade
  - finaliseOrder()

### 4. Lag when loading clients.
**Pattern used:** Lazy Initialisation <br>
**Class(es) affected:** <br>
ClientImpl class only.

### 5. Simpler process for comparing Report objects.
**Pattern used:** Value Object <br>
**Class(es) affected:** <br>
- ReportImpl
- OrderNonScheduled
  - setReport()
  - getReportEmployeeCount()

###  6. Slow database operations for Order creation.

### 7. Multi-threading.
  
