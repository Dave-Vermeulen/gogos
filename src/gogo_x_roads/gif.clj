(ns gogo-x-roads.gif
  (:require [clojure.java.shell :as sh])
  (:import 
    [java.awt Color BasicStroke GradientPaint RenderingHints Graphics2D]
    [java.awt.geom GeneralPath]
    [java.awt.image BufferedImage]
    [javax.imageio ImageIO]
    [java.io File]))

;; Draws a disposable takeaway cup with a tapered (trapezoidal) shape,
;; a curved lid, a decorative stripe, and a friendly face.
;; The cup "shuffles" left then right in a repeating dance.
(defn- draw-professional-cup [^Graphics2D g2d ^Integer frame]
  ;; Enable anti-aliasing for smooth edges.
  (.setRenderingHint g2d RenderingHints/KEY_ANTIALIASING RenderingHints/VALUE_ANTIALIAS_ON)
  
  (let [period         15
        dx             (* 10 (Math/sin (* 2 Math/PI (/ frame period)))) ; horizontal movement (shuffle)
        dy             (* 3 (Math/cos (* 2 Math/PI (/ frame period))))  ; slight vertical bounce
        angle          (* Math/PI 0.005 frame)                           ; very subtle rotation
        cup-x          60
        cup-y          60                                             ; base position of the cup
        cup-top-width  80                                             ; width at the top rim
        cup-bottom-width 60                                           ; width at the bottom (tapered)
        cup-height     100                                            ; height of the cup
        lid-height     10]                                            ; thickness of the lid

    ;; Apply the dynamic translation and rotation for the dancing effect.
    (.translate g2d (+ cup-x dx) (+ cup-y dy))
    (.rotate g2d angle)
    
    ;; Draw a subtle drop shadow below the cup.
    (let [shadow-width 90
          shadow-height 12
          shadow-color (Color. 0 0 0 50)]
      (.setColor g2d shadow-color)
      (.fillOval g2d 5 90 shadow-width shadow-height))
    
    ;; Draw the cup body.
    ;; Use a very clean white-to-off-white gradient (for a modern disposable cup look).
    (let [gradient (GradientPaint. 0 0 Color/WHITE 0 cup-height (Color. 245 245 245))]
      (.setPaint g2d gradient)
      (.fillRoundRect g2d 0 0 cup-top-width cup-height 20 20)
      (.setColor g2d (Color. 62 39 35)) ; Deep coffee brown outline (#3E2723)
      (.setStroke g2d (BasicStroke. 2))
      (.drawRoundRect g2d 0 0 cup-top-width cup-height 20 20))
    
    ;; Draw a decorative stripe—a subtle, semi-transparent Islamic green band.
    (let [y0 (* 0.4 cup-height)
          y1 (* 0.6 cup-height)
          delta (/ (- cup-top-width cup-bottom-width) 2)
          x-left0 (* delta (/ y0 (double cup-height)))
          x-right0 (- cup-top-width (* delta (/ y0 (double cup-height))))
          x-left1 (* delta (/ y1 (double cup-height)))
          x-right1 (- cup-top-width (* delta (/ y1 (double cup-height))))
          stripe (doto (GeneralPath.)
                   (.moveTo (double x-left0) (double y0))
                   (.lineTo (double x-right0) (double y0))
                   (.lineTo (double x-right1) (double y1))
                   (.lineTo (double x-left1) (double y1))
                   (.closePath))]
      (.setColor g2d (Color. 76 175 80 150)) ; Semi-transparent Islamic green (#4CAF50 with alpha)
      (.fill g2d stripe))
    
    ;; Draw the lid as a curved arc across the top.
    (let [lid-x    0
          lid-y    (- (double lid-height))
          lid-width (double cup-top-width)
          lid-arc-height (* 2 (double lid-height))]
      (.setColor g2d (Color. 220 220 220))      ; Light grey lid
      (.fillArc g2d (int lid-x) (int lid-y) (int lid-width) (int lid-arc-height) 0 180)
      (.setColor g2d (Color. 62 39 35))
      (.drawArc g2d (int lid-x) (int lid-y) (int lid-width) (int lid-arc-height) 0 180))
    
    ;; Draw a friendly face.
    (let [eye-diameter 8
          eye-y 15]   ; adjusted position relative to the lid
      (.setColor g2d Color/BLACK)
      (.fillOval g2d 20 eye-y eye-diameter eye-diameter)
      (.fillOval g2d 50 eye-y eye-diameter eye-diameter))
    (.setStroke g2d (BasicStroke. 2))
    (.drawArc g2d 20 25 40 20 180 180)            ; Smile
    
    ;; Add a subtle reflective highlight.
    (let [highlight-color (Color. 255 255 255 100)]
      (.setColor g2d highlight-color)
      (.fillOval g2d 10 10 20 10))
    
    ;; Revert transformation.
    (.rotate g2d (- angle))
    (.translate g2d (- (+ cup-x dx)) (- (+ cup-y dy)))))

(defn generate-coffee-gif! []
  (let [width 200
        height 200
        frames 30                      ;; Increased frame count for smoother animation.
        output-dir (File. "resources/public/images")]
    (.mkdirs output-dir)
    (println "Output directory is:" (.getAbsolutePath output-dir))
    
    ;; Generate each frame.
    (doseq [i (range frames)]
      (let [img (BufferedImage. width height BufferedImage/TYPE_INT_ARGB)
            g2d (.createGraphics img)]
        ;; Set a background that fits the site's coffee-inspired palette.
        (.setBackground g2d (Color. 0 0 0 0)) ; off-white background
        (.clearRect g2d 0 0 width height)
        (draw-professional-cup g2d i)
        (.dispose g2d)
        (ImageIO/write img "png" (File. (format "resources/public/images/frame_%02d.png" i)))))
    
    ;; Use ImageMagick to stitch the frames into an animated GIF.
    (let [cmd (str "convert -delay 5 -loop 0 '"
                   (.getAbsolutePath output-dir)
                   "/frame_*.png' '"
                   (.getAbsolutePath output-dir)
                   "/coffee_dance.gif'")]
      (println "Running command:" cmd)
      (let [result (sh/sh "bash" "-c" cmd)]
        (println "convert output:" result)))))

