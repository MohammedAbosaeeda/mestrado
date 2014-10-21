"PictureMotionEstimator = {width = 176, height = 144} function PictureMotionEstimator:new(o)     o = o or {}           o['inner'] = new_native_pme(o['width'], o['height'])          setmetatable(o, self)     self.__index = self          return o end  function PictureMotionEstimator:match(currentPicture, referencePicture)     return pme_native_match(self.inner, currentPicture, referencePicture) end   function createPicture(width, height, dataSet)     return NativeTestSupport_createPicture(width, height, dataSet) end   function testPictureMotionCounterpart(pmc, width, height, currentPicture, referencePicture)     NativeTestSupport_testPictureMotionCounterpart(pmc, width, height, currentPicture, referencePicture) end       function launch()     print('DMEC APP for KESO is alive!');               pictureWidth = 176     pictureHeight = 144     pme = PictureMotionEstimator:new{width = pictureWidth, height = pictureHeight}          currentPicture = createPicture(pictureWidth, pictureHeight, 0)     referencePicture = createPicture(pictureWidth, pictureHeight, 1);          pmc = pme:match(currentPicture, referencePicture);          testPictureMotionCounterpart(pmc, pictureWidth, pictureHeight, currentPicture, referencePicture);          print('Bye!') end   launch()"