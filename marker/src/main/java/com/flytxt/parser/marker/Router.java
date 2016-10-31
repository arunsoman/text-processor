package com.flytxt.parser.marker;

public final class Router implements Comparable<int[]> {

    private int[] markerPosition;

    private int[] indexPosition;

    public void set(int[] markerPosition) {
        this.markerPosition = new int[markerPosition.length];
        System.arraycopy(markerPosition, 0, this.markerPosition, 0, markerPosition.length);
        indexPosition = new int[markerPosition.length];
        setIndexPositionArray(markerPosition);
    }

    private void setIndexPositionArray(int[] array) {
        int n = array.length;
        for (int j = 1; j < n; j++) {
            int key = array[j];
            int i = j - 1;
            while ((i > -1) && (array[i] > key)) {
                array[i + 1] = array[i];
                indexPosition[i + 1] = indexPosition[i];
                i--;
            }
            array[i + 1] = key;
            indexPosition[i + 1] = j;
        }
    }

    public int getMarkerPosition(int pointer) {
        return markerPosition[indexPosition[pointer]];
    }

    @Override
    public int compareTo(int[] o) {
        if (markerPosition.length != o.length)
            return -1;
        for (int i = 0; i < o.length; i++)
            if (markerPosition[i] != o[i])
                return -1;
        return 0;
    }
}