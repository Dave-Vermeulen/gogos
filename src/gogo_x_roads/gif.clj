(ns gogo-x-roads.gif
    (:require [clojure.java.shell :as sh])
    (:import [java.awt Color Graphics2D]
           [java.awt.image BufferedImage]
           [javax.imageio ImageIO]
           [java.io File]
           [gogo_x_roads GifSequenceWriter]
           [javax.imageio.stream FileImageOutputStream]))



(defn- draw-funky-cup [^Graphics2D g2d ^Integer frame]
  (let [x-offset (* 15 (Math/sin (/ frame 1.5))) ; horizontal wobble
        y-offset (* 10 (Math/cos (/ frame 2.0))) ; bounce
        rotation (* 0.3 frame)] ; tilt
    (.rotate g2d rotation (+ 100 x-offset) (+ 100 y-offset))
    (.setColor g2d (Color. 139 69 19)) ; Brown
    (.fillRect g2d (+ 50 x-offset) (+ 50 y-offset) 60 80) ; Cup body
    (.setColor g2d (Color. 255 200 0)) ; Yellow steam, may need to change to light gold mustard
    (.fillOval g2d (+ 70 x-offset) (- 30 y-offset) 20 20)))

(defn generate-coffee-gif! []
  (let [width 200
        height 200
        frames 20
        output-dir (File. "resources/public/images")]
    (.mkdirs output-dir)
    (doseq [i (range frames)]
      (let [img (BufferedImage. width height BufferedImage/TYPE_INT_ARGB)
            g2d (.createGraphics img)]
        (.setBackground g2d (Color/WHITE))
        (.clearRect g2d 0 0 width height)
        (draw-funky-cup g2d i)
        (.dispose g2d)
        (ImageIO/write img "png" (File. (format "resources/public/images/frame_%02d.png" i)))))

    
    (sh/sh "convert" "-delay" "50" "-loop" "0"
            (str (.getAbsolutePath output-dir) "/frame_*.png")
            (str (.getAbsolutePath output-dir) "/coffee_dance.gif"))))