# Passphrases
Everyone knows passphrases. One can choose passphrases from poems, songs, 
movies names and so on but frequently they can be guessed due to common cultural references. 
You can get your passphrases stronger by different means. One is the following:

choose a text in capital letters including or not digits and non alphabetic characters,

shift each letter by a given number but the transformed letter must be a letter (circular shift),
replace each digit by its complement to 9,
keep such as non alphabetic and non digit characters,
downcase each letter in odd position, upcase each letter in even position (the first character is in position 0),
reverse the whole result.
Example:
your text: "BORN IN 2015!", shift 1

+1 "CPSO JO 7984!" // Add 1 and random numbers to replace numbers,

4 "CpSo jO 7984!" // Cappital and not cappital

5 "!4897 Oj oSpC" // reverse it!

With longer passphrases it's better to have a small and easy program. Would you write it?

https://en.wikipedia.org/wiki/Passphrase


## url

https://www.codewars.com/kata/559536379512a64472000053/train/clojure


## test examples

```clojure
(deftest a-test1
  (testing "Test1"
    (is (= (play-pass "I LOVE YOU!!!" 1) "!!!vPz fWpM J"))))

(deftest a-test4
  (testing "Test4"
    (is (= (play-pass "MY GRANMA CAME FROM NY ON THE 23RD OF APRIL 2015" 2) 
           "4897 NkTrC Hq fT67 GjV Pq aP OqTh gOcE CoPcTi aO"))))

````