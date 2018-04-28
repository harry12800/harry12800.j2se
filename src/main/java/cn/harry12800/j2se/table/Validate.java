///*jadclipse*/// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
//
//package cn.harry12800.j2se.table;
//
//import java.util.*;
//import java.util.regex.Pattern;
//
//// Referenced classes of package org.apache.commons.lang3:
////            StringUtils, ArrayUtils
//
//public class Validate
//{
//
//    public Validate()
//    {
//    }
//
//    public static void isTrue(boolean expression, String message, long value)
//    {
//        if(!expression)
//            throw new IllegalArgumentException(String.format(message, new Object[] {
//                Long.valueOf(value)
//            }));
//        else
//            return;
//    }
//
//    public static void isTrue(boolean expression, String message, double value)
//    {
//        if(!expression)
//            throw new IllegalArgumentException(String.format(message, new Object[] {
//                Double.valueOf(value)
//            }));
//        else
//            return;
//    }
//
//    public static void isTrue(boolean expression, String message, Object values[])
//    {
//        if(!expression)
//            throw new IllegalArgumentException(String.format(message, values));
//        else
//            return;
//    }
//
//    public static void isTrue(boolean expression)
//    {
//        if(!expression)
//            throw new IllegalArgumentException("The validated expression is false");
//        else
//            return;
//    }
//
//    public static Object notNull(Object object)
//    {
//        return notNull(object, "The validated object is null", new Object[0]);
//    }
//
//    public static Object notNull(Object object, String message, Object values[])
//    {
//        if(object == null)
//            throw new NullPointerException(String.format(message, values));
//        else
//            return object;
//    }
//
//    public static  Object[] notEmpty(Object array[], String message, Object values[])
//    {
//        if(array == null)
//            throw new NullPointerException(String.format(message, values));
//        if(array.length == 0)
//            throw new IllegalArgumentException(String.format(message, values));
//        else
//            return array;
//    }
//
//    public static Object[] notEmpty(Object array[])
//    {
//        return notEmpty(array, "The validated array is empty", new Object[0]);
//    }
//
//    public static  Collection notEmpty(Collection collection, String message, Object values[])
//    {
//        if(collection == null)
//            throw new NullPointerException(String.format(message, values));
//        if(collection.isEmpty())
//            throw new IllegalArgumentException(String.format(message, values));
//        else
//            return collection;
//    }
//
//    public static Collection notEmpty(Collection collection)
//    {
//        return notEmpty(collection, "The validated collection is empty", new Object[0]);
//    }
//
//    public static  Map notEmpty(Map map, String message, Object values[])
//    {
//        if(map == null)
//            throw new NullPointerException(String.format(message, values));
//        if(map.isEmpty())
//            throw new IllegalArgumentException(String.format(message, values));
//        else
//            return map;
//    }
//
//    public static Map notEmpty(Map map)
//    {
//        return notEmpty(map, "The validated map is empty", new Object[0]);
//    }
//
//    public static  CharSequence notEmpty(CharSequence chars, String message, Object values[])
//    {
//        if(chars == null)
//            throw new NullPointerException(String.format(message, values));
//        if(chars.length() == 0)
//            throw new IllegalArgumentException(String.format(message, values));
//        else
//            return chars;
//    }
//
//    public static CharSequence notEmpty(CharSequence chars)
//    {
//        return notEmpty(chars, "The validated character sequence is empty", new Object[0]);
//    }
//
//    public static  CharSequence notBlank(CharSequence chars, String message, Object values[])
//    {
//        if(chars == null)
//            throw new NullPointerException(String.format(message, values));
//        if(StringUtils.isBlank(chars))
//            throw new IllegalArgumentException(String.format(message, values));
//        else
//            return chars;
//    }
//
//    public static CharSequence notBlank(CharSequence chars)
//    {
//        return notBlank(chars, "The validated character sequence is blank", new Object[0]);
//    }
//
//    public static  Object[] noNullElements(Object array[], String message, Object values[])
//    {
//        notNull(((Object) (array)));
//        for(int i = 0; i < array.length; i++)
//            if(array[i] == null)
//            {
//                Object values2[] = ArrayUtils.add(values, Integer.valueOf(i));
//                throw new IllegalArgumentException(String.format(message, values2));
//            }
//
//        return array;
//    }
//
//    public static Object[] noNullElements(Object array[])
//    {
//        return noNullElements(array, "The validated array contains null element at index: %d", new Object[0]);
//    }
//
//    public static  Iterable noNullElements(Iterable iterable, String message, Object values[])
//    {
//        notNull(iterable);
//        int i = 0;
//        for(Iterator it = iterable.iterator(); it.hasNext();)
//        {
//            if(it.next() == null)
//            {
//                Object values2[] = ArrayUtils.addAll(values, new Object[] {
//                    Integer.valueOf(i)
//                });
//                throw new IllegalArgumentException(String.format(message, values2));
//            }
//            i++;
//        }
//
//        return iterable;
//    }
//
//    public static Iterable noNullElements(Iterable iterable)
//    {
//        return noNullElements(iterable, "The validated collection contains null element at index: %d", new Object[0]);
//    }
//
//    public static  Object[] validIndex(Object array[], int index, String message, Object values[])
//    {
//        notNull(((Object) (array)));
//        if(index < 0 || index >= array.length)
//            throw new IndexOutOfBoundsException(String.format(message, values));
//        else
//            return array;
//    }
//
//    public static Object[] validIndex(Object array[], int index)
//    {
//        return validIndex(array, index, "The validated array index is invalid: %d", new Object[] {
//            Integer.valueOf(index)
//        });
//    }
//
//    public static  Collection validIndex(Collection collection, int index, String message, Object values[])
//    {
//        notNull(collection);
//        if(index < 0 || index >= collection.size())
//            throw new IndexOutOfBoundsException(String.format(message, values));
//        else
//            return collection;
//    }
//
//    public static Collection validIndex(Collection collection, int index)
//    {
//        return validIndex(collection, index, "The validated collection index is invalid: %d", new Object[] {
//            Integer.valueOf(index)
//        });
//    }
//
//    public static  CharSequence validIndex(CharSequence chars, int index, String message, Object values[])
//    {
//        notNull(chars);
//        if(index < 0 || index >= chars.length())
//            throw new IndexOutOfBoundsException(String.format(message, values));
//        else
//            return chars;
//    }
//
//    public static CharSequence validIndex(CharSequence chars, int index)
//    {
//        return validIndex(chars, index, "The validated character sequence index is invalid: %d", new Object[] {
//            Integer.valueOf(index)
//        });
//    }
//
//    public static void validState(boolean expression)
//    {
//        if(!expression)
//            throw new IllegalStateException("The validated state is false");
//        else
//            return;
//    }
//
//    public static  void validState(boolean expression, String message, Object values[])
//    {
//        if(!expression)
//            throw new IllegalStateException(String.format(message, values));
//        else
//            return;
//    }
//
//    public static void matchesPattern(CharSequence input, String pattern)
//    {
//        if(!Pattern.matches(pattern, input))
//            throw new IllegalArgumentException(String.format("The string %s does not match the pattern %s", new Object[] {
//                input, pattern
//            }));
//        else
//            return;
//    }
//
//    public static  void matchesPattern(CharSequence input, String pattern, String message, Object values[])
//    {
//        if(!Pattern.matches(pattern, input))
//            throw new IllegalArgumentException(String.format(message, values));
//        else
//            return;
//    }
//
//    public static void inclusiveBetween(Object start, Object end, Comparable value)
//    {
//        if(value.compareTo(start) < 0 || value.compareTo(end) > 0)
//            throw new IllegalArgumentException(String.format("The value %s is not in the specified inclusive range of %s to %s", new Object[] {
//                value, start, end
//            }));
//        else
//            return;
//    }
//
//    public static  void inclusiveBetween(Object start, Object end, Comparable value, String message, Object values[])
//    {
//        if(value.compareTo(start) < 0 || value.compareTo(end) > 0)
//            throw new IllegalArgumentException(String.format(message, values));
//        else
//            return;
//    }
//
//    public static void inclusiveBetween(long start, long end, long value)
//    {
//        if(value < start || value > end)
//            throw new IllegalArgumentException(String.format("The value %s is not in the specified inclusive range of %s to %s", new Object[] {
//                Long.valueOf(value), Long.valueOf(start), Long.valueOf(end)
//            }));
//        else
//            return;
//    }
//
//    public static void inclusiveBetween(long start, long end, long value, String message)
//    {
//        if(value < start || value > end)
//            throw new IllegalArgumentException(String.format(message, new Object[0]));
//        else
//            return;
//    }
//
//    public static void inclusiveBetween(double start, double end, double value)
//    {
//        if(value < start || value > end)
//            throw new IllegalArgumentException(String.format("The value %s is not in the specified inclusive range of %s to %s", new Object[] {
//                Double.valueOf(value), Double.valueOf(start), Double.valueOf(end)
//            }));
//        else
//            return;
//    }
//
//    public static void inclusiveBetween(double start, double end, double value, String message)
//    {
//        if(value < start || value > end)
//            throw new IllegalArgumentException(String.format(message, new Object[0]));
//        else
//            return;
//    }
//
//    public static void exclusiveBetween(Object start, Object end, Comparable value)
//    {
//        if(value.compareTo(start) <= 0 || value.compareTo(end) >= 0)
//            throw new IllegalArgumentException(String.format("The value %s is not in the specified exclusive range of %s to %s", new Object[] {
//                value, start, end
//            }));
//        else
//            return;
//    }
//
//    public static  void exclusiveBetween(Object start, Object end, Comparable value, String message, Object values[])
//    {
//        if(value.compareTo(start) <= 0 || value.compareTo(end) >= 0)
//            throw new IllegalArgumentException(String.format(message, values));
//        else
//            return;
//    }
//
//    public static void exclusiveBetween(long start, long end, long value)
//    {
//        if(value <= start || value >= end)
//            throw new IllegalArgumentException(String.format("The value %s is not in the specified exclusive range of %s to %s", new Object[] {
//                Long.valueOf(value), Long.valueOf(start), Long.valueOf(end)
//            }));
//        else
//            return;
//    }
//
//    public static void exclusiveBetween(long start, long end, long value, String message)
//    {
//        if(value <= start || value >= end)
//            throw new IllegalArgumentException(String.format(message, new Object[0]));
//        else
//            return;
//    }
//
//    public static void exclusiveBetween(double start, double end, double value)
//    {
//        if(value <= start || value >= end)
//            throw new IllegalArgumentException(String.format("The value %s is not in the specified exclusive range of %s to %s", new Object[] {
//                Double.valueOf(value), Double.valueOf(start), Double.valueOf(end)
//            }));
//        else
//            return;
//    }
//
//    public static void exclusiveBetween(double start, double end, double value, String message)
//    {
//        if(value <= start || value >= end)
//            throw new IllegalArgumentException(String.format(message, new Object[0]));
//        else
//            return;
//    }
//
//    public static void isInstanceOf(Class type, Object obj)
//    {
//        if(!type.isInstance(obj))
//            throw new IllegalArgumentException(String.format("Expected type: %s, actual: %s", new Object[] {
//                type.getName(), obj != null ? obj.getClass().getName() : "null"
//            }));
//        else
//            return;
//    }
//
//    public static  void isInstanceOf(Class type, Object obj, String message, Object values[])
//    {
//        if(!type.isInstance(obj))
//            throw new IllegalArgumentException(String.format(message, values));
//        else
//            return;
//    }
//
//    public static void isAssignableFrom(Class superType, Class type)
//    {
//        if(!superType.isAssignableFrom(type))
//            throw new IllegalArgumentException(String.format("Cannot assign a %s to a %s", new Object[] {
//                type != null ? type.getName() : "null", superType.getName()
//            }));
//        else
//            return;
//    }
//
//    public static  void isAssignableFrom(Class superType, Class type, String message, Object values[])
//    {
//        if(!superType.isAssignableFrom(type))
//            throw new IllegalArgumentException(String.format(message, values));
//        else
//            return;
//    }
//
//    private static final String DEFAULT_EXCLUSIVE_BETWEEN_EX_MESSAGE = "The value %s is not in the specified exclusive range of %s to %s";
//    private static final String DEFAULT_INCLUSIVE_BETWEEN_EX_MESSAGE = "The value %s is not in the specified inclusive range of %s to %s";
//    private static final String DEFAULT_MATCHES_PATTERN_EX = "The string %s does not match the pattern %s";
//    private static final String DEFAULT_IS_NULL_EX_MESSAGE = "The validated object is null";
//    private static final String DEFAULT_IS_TRUE_EX_MESSAGE = "The validated expression is false";
//    private static final String DEFAULT_NO_NULL_ELEMENTS_ARRAY_EX_MESSAGE = "The validated array contains null element at index: %d";
//    private static final String DEFAULT_NO_NULL_ELEMENTS_COLLECTION_EX_MESSAGE = "The validated collection contains null element at index: %d";
//    private static final String DEFAULT_NOT_BLANK_EX_MESSAGE = "The validated character sequence is blank";
//    private static final String DEFAULT_NOT_EMPTY_ARRAY_EX_MESSAGE = "The validated array is empty";
//    private static final String DEFAULT_NOT_EMPTY_CHAR_SEQUENCE_EX_MESSAGE = "The validated character sequence is empty";
//    private static final String DEFAULT_NOT_EMPTY_COLLECTION_EX_MESSAGE = "The validated collection is empty";
//    private static final String DEFAULT_NOT_EMPTY_MAP_EX_MESSAGE = "The validated map is empty";
//    private static final String DEFAULT_VALID_INDEX_ARRAY_EX_MESSAGE = "The validated array index is invalid: %d";
//    private static final String DEFAULT_VALID_INDEX_CHAR_SEQUENCE_EX_MESSAGE = "The validated character sequence index is invalid: %d";
//    private static final String DEFAULT_VALID_INDEX_COLLECTION_EX_MESSAGE = "The validated collection index is invalid: %d";
//    private static final String DEFAULT_VALID_STATE_EX_MESSAGE = "The validated state is false";
//    private static final String DEFAULT_IS_ASSIGNABLE_EX_MESSAGE = "Cannot assign a %s to a %s";
//    private static final String DEFAULT_IS_INSTANCE_OF_EX_MESSAGE = "Expected type: %s, actual: %s";
//}
//
//
///*
//	DECOMPILATION REPORT
//
//	Decompiled from: F:\Workspaces\sso_\WebContent\WEB-INF\lib\commons-lang3-3.3.2.jar
//	Total time: 131 ms
//	Jad reported messages/errors:
//The class file version is 50.0 (only 45.3, 46.0 and 47.0 are supported)
//	Exit status: 0
//	Caught exceptions:
//*/