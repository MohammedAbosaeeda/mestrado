-- Author: Mateus Krepsky Ludwich.


-- Fake natives - BEGIN
function new_native_pme(width, height)
    print("Creating Picture Motion Estimator...")
    return nil
end


function pme_native_match(self_inner, currentPicture, referencePicture)
    print("Match...")
    return nil
end

function NativeTestSupport_createPicture(width, height, dataSet)
    print("Creating picture...")
    return nil
end

function NativeTestSupport_testPictureMotionCounterpart(pmc, width, height, currentPicture, referencePicture)
    print("Testing computed PMC...")
end

-- Fake natives - END


-- Lua wrappers for DMEC - BEGIN

-- PictureMotionEstimator - BEGIN
PictureMotionEstimator = {width = 176, height = 144}
function PictureMotionEstimator:new(o)
    o = o or {}   -- create object if user does not provide one
    
    o["inner"] = new_native_pme(o["width"], o["height"])
    
    setmetatable(o, self)
    self.__index = self
    
    return o
end

function PictureMotionEstimator:match(currentPicture, referencePicture)
    return pme_native_match(self.inner, currentPicture, referencePicture)
end

-- PictureMotionEstimator - END


-- NativeTestSupport - BEGIN
function createPicture(width, height, dataSet)
    return NativeTestSupport_createPicture(width, height, dataSet)
end


function testPictureMotionCounterpart(pmc, width, height, currentPicture, referencePicture)
    NativeTestSupport_testPictureMotionCounterpart(pmc, width, height, currentPicture, referencePicture)
end

-- NativeTestSupport - END

-- Lua wrappers for DMEC - END

-- Application - BEGIN

function launch()
    print("DMEC APP for KESO is alive!");

    -- FullHD: 1920x1088, QCIF: 176x144
    pictureWidth = 176
    pictureHeight = 144
    pme = PictureMotionEstimator:new{width = pictureWidth, height = pictureHeight}

    currentPicture = createPicture(pictureWidth, pictureHeight, 0)
    referencePicture = createPicture(pictureWidth, pictureHeight, 1);

    pmc = pme:match(currentPicture, referencePicture);

    testPictureMotionCounterpart(pmc, pictureWidth, pictureHeight, currentPicture, referencePicture);

    print("Bye!")
end


launch()

-- Application - END
