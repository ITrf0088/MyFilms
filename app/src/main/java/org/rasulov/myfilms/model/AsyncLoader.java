package org.rasulov.myfilms.model;

import android.os.AsyncTask;

public class AsyncLoader<I, O> extends AsyncTask<I, Void, O> {

    public interface Task<I, O> {
        O execute(I input);
    }

    public interface PostExecuteTask<O> {
        void onPostExecute(O result);
    }


    private Task<I, O> task;
    private PostExecuteTask<O> postExecuteTask;


    @Override
    protected O doInBackground(I... input) {
        boolean isVoid = input instanceof Void[];
        if (isVoid) {
            return task.execute(null);
        } else if (task != null && input.length > 0 && input[0] != null) {
            return task.execute(input[0]);
        }
        return null;
    }

    @Override
    protected void onPostExecute(O result) {
        if (postExecuteTask != null) {
            postExecuteTask.onPostExecute(result);
        }
    }

    public void setTask(Task<I, O> task) {
        this.task = task;
    }

    public void setPostExecuteTask(PostExecuteTask<O> postExecuteTask) {
        this.postExecuteTask = postExecuteTask;
    }
}
