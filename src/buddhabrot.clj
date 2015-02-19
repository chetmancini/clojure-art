(ns buddhabrot
    (:require [clojure-art.math :refer :all])
    (:require [clojre-art.window :refer :all])
    (:import (javax.swing JFrame JLabel)
      (java.awt Graphics Dimension Color)
      (java.awt.image BufferedImage)))

(defn calc-path
      [x y max-iterations]
      (let  [c [x y]]
            (loop [z c
                   path []
                   iterations 0]
                  (if (> iterations max-iterations)
                    []
                    (if (> (abs z) 2.0)
                      (conj path z)
                      (recur (add c (multiply z z)) (conj path z) (inc iterations)))))))

(defn point-to-coordinate [size [real imag]]
      [(int (+ (* 0.3 size (+ real 0.5)) (/ size 2)))
       (int (+ (* 0.3 size imag) (/ size 2)))])

(defn buffer-set [{size :size buffer :buffer} point]
      (let  [[x y] (point-to-coordinate size point)]
            (if (and (> x 0)
                     (> y 0)
                     (< x size)
                     (< y size))
              (aset-int buffer y x (+ 1 (aget buffer y x))))))

(defn generate [fractal]
      (let  [{:keys [buffer iteration]} fractal]
            (doseq [point (iterate inc 1)]
                   (let  [x    (- (rand 6) 3)
                          y    (- (rand 6) 3)
                          path (calc-path x y iteration)]

                         (if (= (mod point 1000000) 0)
                           (println "Point: " point))

                         (doseq [p path] (buffer-set fractal p))))))

(defn start [fractal]
      (future (generate fractal)))

(defn calc-pixel-color
      [iteration max-iterations]
      (let [gray (int (/ (* iteration 255) max-iterations))
            r    gray
            g    (min (int ( / (* 5 ( * gray gray)) 255)) 255)
            b    (min (int (+ 40 ( / (* 5 (* gray gray)) 255))) 255)]
           (try
             (Color. r g b)
             (catch Exception e (new Color 0 0 0)))))


(def fractal {:buffer (make-array Integer/TYPE 800 800) :size 800 :iteration 50})

