import java.util.*;

class Student {
    String uid;
    String name;
    int fineAmount;
    int currentBorrowCount;

    public Student(String uid, String name, int fineAmount, int currentBorrowCount) {
        this.uid = uid;
        this.name = name;
        this.fineAmount = fineAmount;
        this.currentBorrowCount = currentBorrowCount;
    }
}
class Asset {
    String assetId;
    String assetName;
    boolean available;
    int securityLevel;

    public Asset(String assetId, String assetName, boolean available, int securityLevel) {
        this.assetId = assetId;
        this.assetName = assetName;
        this.available = available;
        this.securityLevel = securityLevel;
    }
}
class CheckoutRequest {
    String uid;
    String assetId;
    int hoursRequested;

    public CheckoutRequest(String uid, String assetId, int hoursRequested) {
        this.uid = uid;
        this.assetId = assetId;
        this.hoursRequested = hoursRequested;
    }
}
class ValidationUtil {

    public static void validateUid(String uid) {
        if (uid == null || uid.length() < 8 || uid.length() > 12 || uid.contains(" ")) {
            throw new IllegalArgumentException("Invalid UID format");
        }
    }

    public static void validateAssetId(String assetId) {
        if (assetId == null || !assetId.matches("LAB-\\d+")) {
            throw new IllegalArgumentException("Invalid Asset ID format");
        }
    }

    public static void validateHours(int hrs) {
        if (hrs < 1 || hrs > 6) {
            throw new IllegalArgumentException("Hours must be between 1 and 6");
        }
    }
}
class AssetStore {
    Asset[] assets;

    public AssetStore(Asset[] assets) {
        this.assets = assets;
    }

    public Asset findAsset(String assetId) {
        for (Asset a : assets) {
            if (a.assetId.equals(assetId)) {
                return a;
            }
        }
        throw new NullPointerException("Asset not found: " + assetId);
    }

    public void markBorrowed(Asset a) {
        if (!a.available) {
            throw new IllegalStateException("Asset already borrowed");
        }
        a.available = false;
    }
}
class CheckoutService {
    Student[] students;
    AssetStore store;

    public CheckoutService(Student[] students, AssetStore store) {
        this.students = students;
        this.store = store;
    }
    public String checkout(CheckoutRequest req)
            throws IllegalArgumentException, IllegalStateException,
            SecurityException, NullPointerException {

        ValidationUtil.validateUid(req.uid);
        ValidationUtil.validateAssetId(req.assetId);
        ValidationUtil.validateHours(req.hoursRequested);

        Student s = null;
        for (Student st : students) {
            if (st.uid.equals(req.uid)) {
                s = st;
                break;
            }
        }
        if (s == null)
            throw new NullPointerException("Student not found: " + req.uid);
        Asset a = store.findAsset(req.assetId);
        if (s.fineAmount > 0)
            throw new IllegalStateException("Outstanding fine exists");

        if (s.currentBorrowCount >= 2)
            throw new IllegalStateException("Borrow limit exceeded");

        if (!a.available)
            throw new IllegalStateException("Asset not available");

        if (a.securityLevel == 3 && !s.uid.startsWith("KRG"))
            throw new SecurityException("Restricted asset. KRG UID required");

        if (req.hoursRequested == 6) {
            System.out.println("Note: Max duration selected. Return strictly on time.");
        }
        if (a.assetName.contains("Cable") && req.hoursRequested > 3) {
            req.hoursRequested = 3;
            System.out.println("Policy applied: Cables can be issued max 3 hours. Updated to 3.");
        }
        store.markBorrowed(a);
        s.currentBorrowCount++;
    return "TXN-20260221" + a.assetId + "-" + s.uid;
    }
}
class AuditLogger {

    public static void log(String msg) {
        System.out.println("AUDIT: " + msg);
    }

    public static void logError(Exception e) {
        System.out.println("ERROR LOG: " + e.getMessage());
    }
}

public class Main {

    public static void main(String[] args) {

        Student[] students = {
                new Student("KRG20281", "Akshat", 0, 0),
                new Student("ABC12345", "Ansh", 100, 1),
                new Student("KRG99999", "Ankit", 0, 2)
        };
        Asset[] assets = {
                new Asset("LAB-101", "HDMI Cable", true, 1),
                new Asset("LAB-102", "Router Device", true, 3),
                new Asset("LAB-103", "Projector", false, 2)
        };

        AssetStore store = new AssetStore(assets);
        CheckoutService service = new CheckoutService(students, store);

        CheckoutRequest[] requests = {
                new CheckoutRequest("KRG20281", "LAB-101", 5), // Success
                new CheckoutRequest("KRG20281", "LAB-XYZ", 7), // Invalid
                new CheckoutRequest("ABC12345", "LAB-102", 2)  // Policy fail
        };

        for (CheckoutRequest req : requests) {
            try {
                String receipt = service.checkout(req);
                System.out.println("SUCCESS: " + receipt);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid Input: " + e.getMessage());
                AuditLogger.logError(e);
            } catch (NullPointerException e) {
                System.out.println("Data Error: " + e.getMessage());
                AuditLogger.logError(e);
            } catch (SecurityException e) {
                System.out.println("Security Error: " + e.getMessage());
                AuditLogger.logError(e);
            } catch (IllegalStateException e) {
                System.out.println("Policy Violation: " + e.getMessage());
                AuditLogger.logError(e);
            } finally {
                AuditLogger.log("Attempt finished for UID=" + req.uid +", asset=" + req.assetId);
            }
        }
    }
}
