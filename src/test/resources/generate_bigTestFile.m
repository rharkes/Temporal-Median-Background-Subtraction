%% generate
N=1E6; %nr of peaks
IMs=[64,64,3E4]; %image size

IM = zeros(IMs,'uint16');
IM(randperm(numel(IM),N))=10000; %put N peaks

%filter to gaussian
Sigma = 2;
S = 10;
filt = GaussianRH(Sigma,S,2);
file = 'bigTestfile.tif';
F = Fast_Tiff_Write(file,1);
for ct = 1:size(IM,3)
    IM(:,:,ct) = uint16(200+conv2(IM(:,:,ct),filt,'same')+randn(IMs(1:2))*50);
    F.WriteIMG(IM(:,:,ct));
end
F.close;
%%
window = 501;
windowC = floor(window/2);
offset = 1000;
MED = movmedian(IM,501,3,'Endpoints','discard');
%%
file = 'bigResultfile.tif';
F = Fast_Tiff_Write(file,1);
for ct = 1:size(IM,3)
    if ct<=windowC
        F.WriteIMG((offset+IM(:,:,ct))-MED(:,:,1))
    elseif ct<(IMs(3)-windowC)
        F.WriteIMG((offset+IM(:,:,ct))-MED(:,:,ct-windowC))
    else
        F.WriteIMG((offset+IM(:,:,ct))-MED(:,:,end))
    end
end
F.close;