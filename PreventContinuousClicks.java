        //Prevent continuous clicks
        CountDownTimer countDownTimer = new CountDownTimer(6000, 1000) {
            public void onTick(long millisUntilFinished) {
                mNextButton.setEnabled(false);
            }
            public void onFinish() {
                mNextButton.setEnabled(true);
            }
        }.start();