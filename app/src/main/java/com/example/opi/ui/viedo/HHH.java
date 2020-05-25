

package com.example.opi.ui.viedo;
        import android.net.Uri;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.MediaController;
        import android.widget.VideoView;

        import androidx.annotation.NonNull;
        import androidx.fragment.app.Fragment;
        import androidx.lifecycle.ViewModelProviders;
        import com.example.opi.R;


public class HHH extends Fragment {
VideoView videoView;
    private Viedo hhh;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        hhh = ViewModelProviders.of(this).get(Viedo.class);
        View root = inflater.inflate(R.layout.viedo, container, false);
        MediaController mediaController=new MediaController(getActivity());
        videoView=root.findViewById(R.id.videoView);
videoView.setMediaController(mediaController);
mediaController.setAnchorView(videoView);
        Uri uri=Uri.parse("https://firebasestorage.googleapis.com/v0/b/mica1-af3e8.appspot.com/o/oioio.mp4?alt=media&token=dc8ff561-2c96-4684-8725-de675dbb1a04");
        videoView.setVideoURI(uri);
        videoView.start();







        return root;
    }
}