package com.intuitive.yummy;

public enum RestResultCode {
    	RUNNING(0),
    	FINISHED(1),
    	ERROR(2);
    	
    	private final int value;
        private RestResultCode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
}
