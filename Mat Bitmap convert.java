Mat mat = new Mat();

//Mat bitmap to Mat
Bitmap bmp32 = bitmap_color.copy(Bitmap.Config.ARGB_8888, true);
Utils.bitmapToMat(bmp32, mat);

//Mat to bitmap
Bitmap bitmap = Bitmap.createBitmap(mat.width(), mat.height(), Bitmap.Config.ARGB_8888);
Utils.matToBitmap(mat, bitmap,true);
imageView_color.setImageBitmap(bitmap);