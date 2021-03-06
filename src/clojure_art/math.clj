(ns clojure-art.math)

(defn add
      "Complex addition"
      [c1 c2]
      (map + c1 c2))

(defn multiply
      "Complex Multipication"
      [[real-a imag-a] [real-b imag-b]]
      [(- (* real-a real-b)
          (* imag-a imag-b))

       (+ (* real-a imag-b)
          (* imag-a real-b))])

(defn abs
      "Complex Absulute Value"
      [[real imag]]
      (Math/sqrt
        (+ (* real real)
           (* imag imag))))