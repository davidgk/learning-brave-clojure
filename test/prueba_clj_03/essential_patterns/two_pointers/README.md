# Title

Check if there exists a pair with a target sum in a sorted array.


## examples

```java
public boolean hasPairWithSum(int[] arr, int target) {
    int left = 0, right = arr.length - 1;
    while (left < right) {
        int sum = arr[left] + arr[right];
        if (sum == target) return true;
        if (sum < target) left++;
        else right--;
    }
    return false;
}
````