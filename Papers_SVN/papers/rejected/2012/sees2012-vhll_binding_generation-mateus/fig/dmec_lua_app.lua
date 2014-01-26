function launch()
  w = 1920
  h = 1088
  
  pme = 
   PictureMotionEstimator:new{width = w, height = h}
  
  -- cp and rp pictures selection...
  mvsc = pme:match(cp, rp)

  -- Results processing...

end

launch()
