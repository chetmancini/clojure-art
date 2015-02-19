(ns clojure-art.window
    (:import (javax.swing JFrame JLabel)
      (java.awt Graphics Dimension Color)
      (java.awt.image BufferedImage)))

(defn paint-canvas [buffer size graphics]
      (let  [biggest  (apply max (map #(apply max %) buffer))]
            (doseq [y (range size)
                    x (range size)]

                   (.setColor graphics (calc-pixel-color (aget buffer y x) biggest))
                   (.drawLine graphics x y x y))))


(defn draw [{buffer :buffer size :size}]
      (let [image  (BufferedImage. size size BufferedImage/TYPE_INT_RGB)
            canvas (proxy [JLabel] []
                          (paint [g] (.drawImage g image 0 0 this)))]
           (paint-canvas buffer size (.createGraphics image))
           (doto (JFrame.)
                 (.add canvas)
                 (.setSize (Dimension. size size))
                 (.show))))