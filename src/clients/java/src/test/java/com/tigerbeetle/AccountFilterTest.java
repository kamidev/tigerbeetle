package com.tigerbeetle;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class AccountFilterTest {

    @Test
    public void testDefaultValues() {
        final var accountFilter = new AccountFilter();
        assertEquals(0L, accountFilter.getAccountId(UInt128.LeastSignificant));
        assertEquals(0L, accountFilter.getAccountId(UInt128.MostSignificant));
        assertEquals(0L, accountFilter.getTimestampMin());
        assertEquals(0L, accountFilter.getTimestampMax());
        assertEquals(0, accountFilter.getLimit());
        assertEquals(false, accountFilter.getDebits());
        assertEquals(false, accountFilter.getCredits());
        assertEquals(false, accountFilter.getReversed());
    }

    @Test
    public void testAccountId() {
        final var accountFilter = new AccountFilter();

        accountFilter.setAccountId(100, 200);
        assertEquals(100L, accountFilter.getAccountId(UInt128.LeastSignificant));
        assertEquals(200L, accountFilter.getAccountId(UInt128.MostSignificant));
    }

    @Test
    public void testAccountIdLong() {
        final var accountFilter = new AccountFilter();

        accountFilter.setAccountId(100);
        assertEquals(100L, accountFilter.getAccountId(UInt128.LeastSignificant));
        assertEquals(0L, accountFilter.getAccountId(UInt128.MostSignificant));
    }

    @Test
    public void testAccountIdIdAsBytes() {
        final var accountFilter = new AccountFilter();

        final var id = new byte[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6};
        accountFilter.setAccountId(id);
        assertArrayEquals(id, accountFilter.getAccountId());
    }

    @Test
    public void testAccountIdNull() {
        final var accountFilter = new AccountFilter();

        final byte[] id = null;
        accountFilter.setAccountId(id);

        assertArrayEquals(new byte[16], accountFilter.getAccountId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAccountIdInvalid() {
        final var accountFilter = new AccountFilter();

        final var id = new byte[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        accountFilter.setAccountId(id);
        assert false;
    }

    @Test
    public void testUserData128() {
        final var accountFilter = new AccountFilter();

        accountFilter.setUserData128(100, 200);
        assertEquals(100L, accountFilter.getUserData128(UInt128.LeastSignificant));
        assertEquals(200L, accountFilter.getUserData128(UInt128.MostSignificant));
    }

    @Test
    public void testUserData128Long() {
        final var accountFilter = new AccountFilter();

        accountFilter.setUserData128(100);
        assertEquals(100L, accountFilter.getUserData128(UInt128.LeastSignificant));
        assertEquals(0L, accountFilter.getUserData128(UInt128.MostSignificant));
    }

    @Test
    public void testUserData128AsBytes() {
        final var accountFilter = new AccountFilter();

        final var data = new byte[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6};
        accountFilter.setUserData128(data);
        assertArrayEquals(data, accountFilter.getUserData128());
    }

    @Test
    public void testUserData128Null() {
        final var accountFilter = new AccountFilter();

        final byte[] data = null;
        accountFilter.setUserData128(data);

        assertArrayEquals(new byte[16], accountFilter.getUserData128());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUserData128Invalid() {
        final var accountFilter = new AccountFilter();

        final var data = new byte[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        accountFilter.setUserData128(data);
        assert false;
    }

    @Test
    public void testUserData64() {
        final var accountFilter = new AccountFilter();

        accountFilter.setUserData64(100L);
        assertEquals(100L, accountFilter.getUserData64());
    }

    @Test
    public void testUserData32() {
        final var accountFilter = new AccountFilter();

        accountFilter.setUserData32(10);
        assertEquals(10, accountFilter.getUserData32());
    }

    @Test
    public void testCode() {
        final var accountFilter = new AccountFilter();

        accountFilter.setCode(1);
        assertEquals(1, accountFilter.getCode());
    }

    @Test
    public void testReserved() {
        final var accountFilter = new AccountFilterBatch(1);
        accountFilter.add();

        // Empty array:
        final var bytes = new byte[58];
        assertArrayEquals(new byte[58], accountFilter.getReserved());

        // Null == empty array:
        assertArrayEquals(new byte[58], accountFilter.getReserved());
        accountFilter.setReserved(null);

        for (byte i = 0; i < 58; i++) {
            bytes[i] = i;
        }
        accountFilter.setReserved(bytes);
        assertArrayEquals(bytes, accountFilter.getReserved());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReservedInvalid() {
        final var accountFilter = new AccountFilterBatch(1);
        accountFilter.add();
        accountFilter.setReserved(new byte[59]);
        assert false;
    }

    @Test
    public void testTimestampMin() {
        final var accountFilter = new AccountFilter();

        accountFilter.setTimestampMin(100L);
        assertEquals(100, accountFilter.getTimestampMin());
    }

    @Test
    public void testTimestampMax() {
        final var accountFilter = new AccountFilter();

        accountFilter.setTimestampMax(100L);
        assertEquals(100, accountFilter.getTimestampMax());
    }

    @Test
    public void testLimit() {
        final var accountFilter = new AccountFilter();

        accountFilter.setLimit(30);
        assertEquals(30, accountFilter.getLimit());
    }

    @Test
    public void testFlags() {
        // Debits
        {
            final var accountFilter = new AccountFilter();
            accountFilter.setDebits(true);
            assertEquals(true, accountFilter.getDebits());
            assertEquals(false, accountFilter.getCredits());
            assertEquals(false, accountFilter.getReversed());
        }

        // Credits
        {
            final var accountFilter = new AccountFilter();
            accountFilter.setCredits(true);
            assertEquals(false, accountFilter.getDebits());
            assertEquals(true, accountFilter.getCredits());
            assertEquals(false, accountFilter.getReversed());
        }

        // Direction
        {
            final var accountFilter = new AccountFilter();
            accountFilter.setReversed(true);
            assertEquals(false, accountFilter.getDebits());
            assertEquals(false, accountFilter.getCredits());
            assertEquals(true, accountFilter.getReversed());
        }
    }
}
