NULL: Null entries are rejected consistently by throwing an IllegalArgumentException to ensure predictable runtime behavior.

1.) Entries are duplicates when Objects.equals(entry1, entry2). Using == only checks if two variables point to the exact same memory address. Using Objects.equals delegates to T's equals method while handling null checks safely without throwing exceptions.

2.) LinkedSet: Nodes linked together, count is valid, no duplicates, last node points to null, if empty firstNode points to null.
    ResizableArraySet: Elements are packed together without gaps, count is valid, no duplicates, unused array slots are null, if empty count is zero.

3.) remove() returns null on an empty set. Returning null aligns with standard Java container ADT conventions.

4.) Set algebra operations generate new sets without modifying the input. A failure happens if two access groups with different permissions are combined (e.g., Admins and Guests via GroupA.union(groupG)). If union mutates GroupA in place, temporary guests would permanently gain Admin access.

